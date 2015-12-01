package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;

public class Simulation implements ISimulation {
	
	private long nanoSecCounter;
	private World world;
	private AntsAI ai;
	
	public Simulation() {
		world = new World(100, 50, 1);
		ai = new AntsAI();
	}
	
	@Override
	public long getPassedNanoSec() {
		return nanoSecCounter;
	}
	
	@Override
	public void update(long nanoSecDelta) {
		ai.update(world.getAllAnts(), nanoSecDelta);
		nanoSecCounter += nanoSecDelta;
	}

	@Override
	public IWorld getWorld() {
		return world;
	}
	
}
