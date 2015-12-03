package org.uu.lacpp15.g3.antcolony.common;

/**
 * Readable interface of axis aligned box on two-dimensional integer domain.
 * <p>
 * Lower bounds are inclusive and upper bounds exclusive: [xmin,xmax)*[ymin,ymax).
 * @author David
 *
 */
public interface IRAABoxInt2 extends IRAABoxInt {
	public int getMinX();
	public int getMaxX();
	public int getMinY();
	public int getMaxY();
}
