package org.uu.lacpp15.g3.antcolony.simulation;

/**
 * A view on a set of entities in the world, i.e. objects with positions (x,y).
 * @author David
 *
 */
public interface IEntities extends IEntityIterable {
	/**
	 * @return The size of this set.
	 */
	public int size();
}
