package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Ant;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.EntityIterator;

public class Simulation implements ISimulation {
	
	private long nanoSecCounter;
	private World world;
	private AntsAI ai;
	
	public Simulation() {
		final int min = Integer.MIN_VALUE /4;
		final int max = -min;
		world = new World(new WorldBounds(min,max,min,max), 1000, 50000, 1, 10);
		ai = new AntsAI(world);
	}
	
	@Override
	public long getPassedNanoSec() {
		return nanoSecCounter;
	}
	
	@Override
	public void update(long nanoSecDelta) {
		ai.update(nanoSecDelta);
		clampAnts();
		updatePheromones(nanoSecDelta);
		//world.update(nanoSecDelta);
		nanoSecCounter += nanoSecDelta;
	}
	
	private void clampAnts() {
		EntityIterator<Ant> antsIter = world.getAllAnts().iterator();
		while (antsIter.next()) {
			Ant ant = antsIter.getObject();
			ant.clamp(world.getBounds());
		}
	}
	
	private void updatePheromones(long nanoSecDelta) {
		world.getHivePheromoneGrid().update(nanoSecDelta);
		world.getFoodPheromoneGrid().update(nanoSecDelta);
	}

	@Override
	public IWorld getWorld() {
		return world;
	}
	
}
