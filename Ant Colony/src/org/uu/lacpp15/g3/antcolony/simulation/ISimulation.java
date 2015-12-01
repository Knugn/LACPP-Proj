package org.uu.lacpp15.g3.antcolony.simulation;

public interface ISimulation {
	/**
	 * @return the number of nanoseconds that have passed in the simulation.
	 */
	public long getPassedNanoSec();
	/**
	 * Updates the simulation, i.e. steps it forward by a unit of time.
	 * @param nanoSecDelta the number of nanoseconds to progress by.
	 */
	public void update(long nanoSecDelta);
	/**
	 * @return the simulation world.
	 */
	public IWorld getWorld();
}
