package org.uu.lacpp15.g3.antcolony.simulation.entities;

/**
 * An abstract entity iterator with default implementations.
 * @author David
 *
 */
public abstract class AEntityIterator implements IWEntityIterator {
	
	@Override
	public int getx() {
		return getCoord(0);
	}
	
	@Override
	public int gety() {
		return getCoord(1);
	}
	
	@Override
	public void setx(int x) {
		setCoord(0, x);
	}
	
	@Override
	public void sety(int y) {
		setCoord(1, y);
	}
	
}
