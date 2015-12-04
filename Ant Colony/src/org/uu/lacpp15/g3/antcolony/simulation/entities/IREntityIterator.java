package org.uu.lacpp15.g3.antcolony.simulation.entities;

/**
 * An read iterator over entities. 
 * <pre>
 * The intended usage is:
 * {@code
 * while (iter.next()) {
 *    x = iter.getx();
 *    y = iter.gety();
 * }
 * </pre>
 * @author David
 *
 */
public interface IREntityIterator {
	/**
	 * Steps this iterator to the next element.
	 * <p>
	 * Note that this method should be called on a newly created iterator before reading.
	 * 
	 * @return true if this iterator points to an element upon returning, else false.
	 */
	public boolean next();
	/**
	 * @return the id of the current element.
	 */
	public long getId();
	/**
	 * @return the x-coordinate of the current element.
	 * @see #getCoord(int)
	 */
	public int getx();
	/**
	 * @return the y-coordinate of the current element.
	 * @see #getCoord(int)
	 */
	public int gety();
	/**
	 * @param n the index of the coordinate to get, 0 for x and 1 for y.
	 * @return the n-th coordinate of the current element.
	 */
	public int getCoord(int n);
	/**
	 * @return the radisu of the current element.
	 */
	public float getRadius();
}
