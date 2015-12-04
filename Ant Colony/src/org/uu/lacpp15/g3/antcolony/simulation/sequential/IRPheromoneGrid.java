package org.uu.lacpp15.g3.antcolony.simulation.sequential;

/**
 * Readable pheromone gird interface.
 * @author David
 *
 */
public interface IRPheromoneGrid {
	/**
	 * @return the resolution of the grid, which is same along x and y.
	 */
	public int getResolution();
	/**
	 * Retrieve the value of the grid at the given coordinates.
	 * @param x
	 * @param y
	 * @return the pheromone value at (x,y)
	 */
	public float getGridValue(int x, int y);
}