package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import java.util.Random;

import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Ant;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Ants;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.EntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.FoodSource;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Hive;

public class AntsAI {
	
	private World	world;
	private int[][]	prevXY	= new int[2][];
	private Random	random	= new Random();
	
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
			
			EntityIterator<FoodSource> foodIter = world.getAllFoodSources().iterator();
			while (foodIter.next()) {
				FoodSource food = foodIter.getObject();
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
			
			EntityIterator<Hive> hiveIter = world.getAllHives().iterator();
			while (hiveIter.next()) {
				Hive hive = hiveIter.getObject();
				if (ant.collides(hive)) {
					ant.dropFood(hive);
					ant.setHivePheroDrop(1);
					break;
				}
			}
			
			world.getHivePheromoneGrid().dropPheromones(ant.getx(), ant.gety(), ant.getHivePheroDrop());
			world.getFoodPheromoneGrid().dropPheromones(ant.getx(), ant.gety(), ant.getFoodPheroDrop());
			
			ant.scaleFoodPheroDrop((float) Math.pow(0.90, secDelta));
			ant.scaleHivePheroDrop((float) Math.pow(0.90, secDelta));
			
			idx++;
		}
		moveAllAnts(secDelta);
	}
	
	private void moveAllAnts(double secDelta) {
		Ants ants = world.getAllAnts();
		final double frameMaxDelta = ants.getMaxSpeed() * secDelta;
		EntityIterator<Ant> antsIter = ants.iterator();
		int idx = 0;
		while (antsIter.next()) {
			Ant ant = antsIter.getObject();
			final int x = ant.getx();
			final int y = ant.gety();
			final int pdx = x - prevXY[0][idx];
			final int pdy = y - prevXY[1][idx];
			move(frameMaxDelta, ant, pdx, pdy);
			prevXY[0][idx] = x;
			prevXY[1][idx] = y;
			idx++;
		}
	}
	
	private void move(double frameMaxDelta, Ant ant, final int pdx, final int pdy) {
		double ndx, ndy;
		if (pdx == 0 && pdy == 0) {
			double a = random.nextDouble() * 2 * Math.PI;
			ndx = Math.cos(a);
			ndy = Math.sin(a);
		}
		else {
			final double r = random.nextGaussian() / 10;
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
}
