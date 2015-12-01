package org.uu.lacpp15.g3.antcolony.simulation;

/**
 * An iterator over entities. 
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
public interface IEntityIterator {
	/**
	 * Steps this iterator to the next element.
	 * <p>
	 * Note that this method should be called on a newly created iterator before reading.
	 * 
	 * @return true if this iterator points to an element upon returning, else false.
	 */
	public boolean next();
	/**
	 * @return the x-coordinate of the current element.
	 */
	public int getx();
	/**
	 * @return the y-coordinate of the current element.
	 */
	public int gety();
	/**
	 * 
	 * @param n the index of the coordinate to get, 0 for x and 1 for y.
	 * @return the n-th coordinate of the current element.
	 */
	public int getCoord(int n);
}
