package org.uu.lacpp15.g3.antcolony.simulation;

/**
 * Readable pheromone gird interface.
 * @author David
 *
 */
public interface IRPheromoneGrid {
	/**
	 * @return the resolution of the grid along x.
	 */
	public int getResolutionX();
	/**
	 * @return the resolution of the grid along y.
	 */
	public int getResolutionY();
	/**
	 * Retrieve the value of the grid at the given grid coordinates.
	 * @param x
	 * @param y
	 * @return the pheromone value at (x,y)
	 */
	public float getGridValue(int x, int y);
}