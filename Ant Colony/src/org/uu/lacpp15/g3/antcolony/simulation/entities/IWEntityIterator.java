package org.uu.lacpp15.g3.antcolony.simulation.entities;

public interface IWEntityIterator extends IREntityIterator {
	public void setx(int x);
	public void sety(int y);
	public void setCoord(int dim, int value);
	public void setRadius(float r);
}
