package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.common.AABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;

public class Simulation implements ISimulation {
	
	private long nanoSecCounter;
	private World world;
	private AntsAI ai;
	
	public Simulation() {
		final int min = Integer.MIN_VALUE /4;
		final int max = -min;
		world = new World(new AABoxInt2(min,max,min,max), 100, 50, 1);
		ai = new AntsAI(world.getAllAnts());
	}
	
	@Override
	public long getPassedNanoSec() {
		return nanoSecCounter;
	}
	
	@Override
	public void update(long nanoSecDelta) {
		ai.update(nanoSecDelta);
		world.update(nanoSecDelta);
		nanoSecCounter += nanoSecDelta;
	}

	@Override
	public IWorld getWorld() {
		return world;
	}
	
}
