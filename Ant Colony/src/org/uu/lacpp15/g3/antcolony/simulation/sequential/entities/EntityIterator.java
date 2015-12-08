package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

import java.util.Iterator;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;

public class EntityIterator<T extends Entity> extends AEntityIterator {
	
	private Iterator<T>	iter;
	private T			cur;
	
	public EntityIterator(Iterator<T> iter) {
		this.iter = iter;
	}
	
	@Override
	public boolean next() {
		if (iter != null && iter.hasNext()) {
			cur = iter.next();
			return true;
		}
		return false;
	}
	
	@Override
	public long getId() {
		return cur.getId();
	}
	
	@Override
	public int getCoord(int dim) {
		return cur.getCoord(dim);
	}
	
	@Override
	public void setCoord(int dim, int value) {
		cur.setCoord(dim, value);
	}
	
	@Override
	public float getRadius() {
		return cur.getRadius();
	}
	
	@Override
	public void setRadius(float r) {
		cur.setRadius(r);
	}
	
	public T getObject() {
		return cur;
	}
	
}
