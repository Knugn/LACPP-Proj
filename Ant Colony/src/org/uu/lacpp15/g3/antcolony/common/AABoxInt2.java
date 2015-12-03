package org.uu.lacpp15.g3.antcolony.common;

/**
 * Axis aligned box on two-dimensional integer domain.
 * @author David
 *
 */
public class AABoxInt2 implements IRAABoxInt2 {
	
	public final int xmin, xmax, ymin, ymax;
	
	public AABoxInt2(int xmin, int xmax, int ymin, int ymax) {
		if (xmin > xmax || ymin > ymax)
			throw new IllegalArgumentException("xmin > xmax or ymin > ymax");
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}

	@Override
	public int getMinX() {
		return xmin;
	}

	@Override
	public int getMaxX() {
		return xmax;
	}

	@Override
	public int getMinY() {
		return ymin;
	}

	@Override
	public int getMaxY() {
		return ymax;
	}

	@Override
	public int getMin(int dim) {
		switch(dim) {
			case 0:
				return xmin;
			case 1:
				return ymin;
			default:
				throw new IllegalArgumentException("Invalid dimension.");
		}
	}

	@Override
	public int getMax(int dim) {
		switch(dim) {
			case 0:
				return xmax;
			case 1:
				return ymax;
			default:
				throw new IllegalArgumentException("Invalid dimension.");
		}
	}

	@Override
	public int getDim() {
		return 2;
	}

}
