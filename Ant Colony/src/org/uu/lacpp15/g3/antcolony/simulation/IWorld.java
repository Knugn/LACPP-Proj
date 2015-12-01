package org.uu.lacpp15.g3.antcolony.simulation;

/**
 * Interface of the simulation world.
 * @author David
 *
 */
public interface IWorld {
	public IEntities getAllEntities();
	public IAnts getAllAnts();
	public IHives getAllHives();
}
