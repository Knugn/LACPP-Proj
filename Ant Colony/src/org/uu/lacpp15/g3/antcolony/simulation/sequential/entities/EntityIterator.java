package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

import java.util.ArrayList;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;

public class EntityIterator<T extends Entity> extends AEntityIterator {
	
	private ArrayList<T>	arr;
	private int				idx	= 0;
	private T				cur;
	
	public EntityIterator(ArrayList<T> arr) {
		this.arr = arr;
	}
	
	@Override
	public boolean next() {
		if (arr != null && idx < arr.size()) {
			cur = arr.get(idx++);
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
