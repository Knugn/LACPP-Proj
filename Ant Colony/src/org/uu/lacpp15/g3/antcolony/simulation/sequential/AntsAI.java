package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Ant;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Ants;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.EntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.FoodSource;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.FoodSources;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Hive;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Hives;

public class AntsAI {
	
	private World	world;
	private int[][]	prevXY	= new int[2][];
	//private Random	random	= new Random();
	private ForkJoinPool forkJoinPool;
	
	public AntsAI(World world) {
		if (world == null)
			throw new IllegalArgumentException();
		this.world = world;
		Ants ants = world.getAllAnts();
		this.prevXY[0] = new int[ants.size()];
		this.prevXY[1] = new int[ants.size()];
		EntityIterator<Ant> antsIter = ants.iterator();
		int idx = 0;
		while (antsIter.next()) {
			for (int n = 0; n < 2; n++) {
				prevXY[n][idx] = antsIter.getCoord(n);
			}
			idx++;
		}
	}
	
	public void update(long nanoSecDelta) {
		final double secDelta = nanoSecDelta / 1000000000.0;
		
		Ants ants = world.getAllAnts();
		EntityIterator<Ant> antsIter = ants.iterator();
		int idx = 0;
		while (antsIter.next()) {
			Ant ant = antsIter.getObject();
			
			//EntityIterator<FoodSource> foodIter = world.getAllFoodSources().iterator();
			//while (foodIter.next()) {
			//	FoodSource food = foodIter.getObject();
			FoodSources foodSources = world.getAllFoodSources();
			for (int i=0; i<foodSources.size(); i++) {
				FoodSource food = foodSources.get(i);
				if (food.hasFood() && ant.collides(food)) {
					ant.setFoodPheroDrop(1);
					if (!ant.hasFood()) {
						ant.takeFood(food, 1);
						prevXY[0][idx] = ant.getx();
						prevXY[1][idx] = ant.gety();
					}
					break;
				}
			}
			
			//EntityIterator<Hive> hiveIter = world.getAllHives().iterator();
			//while (hiveIter.next()) {
			//	Hive hive = hiveIter.getObject();
			Hives hives = world.getAllHives();
			for (int i=0; i<hives.size(); i++) {
				Hive hive = hives.get(i);
				if (ant.collides(hive)) {
					ant.dropFood(hive);
					ant.setHivePheroDrop(1);
					break;
				}
			}
			
			idx++;
		}
		updatePheromones(secDelta);
		//moveAllAnts(secDelta);
		moveAllAntsParallel(secDelta);
	}
	
	private void updatePheromones(double secDelta) {
		Ants ants = world.getAllAnts();
		for (int i = 0; i < ants.size(); i++) {
			Ant ant = ants.get(i);
			
			world.getHivePheromoneGrid().dropPheromones(ant.getx(), ant.gety(), ant.getHivePheroDrop());
			world.getFoodPheromoneGrid().dropPheromones(ant.getx(), ant.gety(), ant.getFoodPheroDrop());
			
			float scale = (float) Math.pow(0.90, secDelta);
			ant.scaleHivePheroDrop(scale);
			ant.scaleFoodPheroDrop(scale);
		}
		float scale = (float) Math.pow(0.93, secDelta);
		world.getHivePheromoneGrid().scalePheromones(scale);
		world.getFoodPheromoneGrid().scalePheromones(scale);
	}
	
	private void moveAllAnts(double secDelta) {
		moveAllAnts(secDelta,0,world.getAllAnts().size());
	}
	
	private void moveAllAntsParallel(double secDelta) {
		if (forkJoinPool == null)
			forkJoinPool = new ForkJoinPool();
		forkJoinPool.invoke(new MoveAntsTask(secDelta, 0,world.getAllAnts().size()));
	}
	
	private void moveAllAnts(double secDelta, int startIdx, int endIdx) {
		Ants ants = world.getAllAnts();
		final double frameMaxDelta = ants.getMaxSpeed() * secDelta;
		for (int i = startIdx; i < endIdx; i++) {
			Ant ant = ants.get(i);
			final int x = ant.getx();
			final int y = ant.gety();
			final int pdx = x - prevXY[0][i];
			final int pdy = y - prevXY[1][i];
			move(frameMaxDelta, ant, pdx, pdy);
			prevXY[0][i] = x;
			prevXY[1][i] = y;
		}
	}
	
	private void move(double frameMaxDelta, Ant ant, final int pdx, final int pdy) {
		Random random = ThreadLocalRandom.current();
		double ndx, ndy;
		if (pdx == 0 && pdy == 0) {
			double a = random.nextDouble() * 2 * Math.PI;
			ndx = Math.cos(a);
			ndy = Math.sin(a);
		}
		else {
			//final double r = random.nextGaussian() / 10;
			final double r = Math.tan(random.nextDouble()*Math.PI)/100;
			ndx = pdx - pdy * r;
			ndy = pdy + pdx * r;
			double mag = Math.sqrt(ndx * ndx + ndy * ndy);
			ndx /= mag;
			ndy /= mag;
		}
		if (random.nextDouble() < 0.95) {
			PheromoneGrid pheroGrid = null;
			if (ant.hasFood())
				pheroGrid = world.getHivePheromoneGrid();
			else
				pheroGrid = world.getFoodPheromoneGrid();
			int xIdx = world.getBounds().getChunkIndexX(ant.getx(), pheroGrid.getResolutionX());
			int yIdx = world.getBounds().getChunkIndexY(ant.gety(), pheroGrid.getResolutionY());
			double gradx = pheroGrid.getGridGradientX(xIdx, yIdx);
			double grady = pheroGrid.getGridGradientY(xIdx, yIdx);
			double gradMag = Math.sqrt(gradx * gradx + grady * grady);
			if (gradMag > 0.1) {
				gradx *= 0.10 / gradMag;
				grady *= 0.10 / gradMag;
				ndx += gradx;
				ndy += grady;
			}
		}
		double f = frameMaxDelta / Math.sqrt(ndx * ndx + ndy * ndy);
		ndx *= f;
		ndy *= f;
		ant.move((int) ndx, (int) ndy);
		ant.clamp(world.getBounds());
	}
	
	private class MoveAntsTask extends RecursiveAction {
		
		static final int cutoff = 64;
		final double secDelta;
		final int startIdx, endIdx;
		
		public MoveAntsTask(double secDelta, int startIdx, int endIdx) {
			this.secDelta = secDelta;
			this.startIdx = startIdx;
			this.endIdx = endIdx;
		}
		
		@Override
		protected void compute() {
			if (endIdx - startIdx <= cutoff) {
				moveAllAnts(secDelta, startIdx, endIdx);
			}
			else {
				int midIdx = (startIdx+endIdx)/2;
				invokeAll(
						new MoveAntsTask(secDelta, startIdx, midIdx), 
						new MoveAntsTask(secDelta, midIdx, endIdx));
			}
		}
		
	}
}
