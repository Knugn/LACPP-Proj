package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

import java.util.ArrayList;
import java.util.Collection;

import org.uu.lacpp15.g3.antcolony.simulation.entities.IWEntities;

public class GenericEntities<T extends Entity> implements IWEntities {
	
	private ArrayList<T> entities;
	
	public GenericEntities(int maxEntities) {
		entities = new ArrayList<>(maxEntities);
	}
	
	@Override
	public int size() {
		return entities.size();
	}

	@Override
	public EntityIterator<T> iterator() {
		return new EntityIterator<>(entities);
	}
	
	public void add(T e) {
		if (e == null)
			throw new IllegalArgumentException("e must not be null.");
		entities.add(e);
	}
	
	public boolean remove(T e) {
		return entities.remove(e);
	}

	public boolean isEmpty() {
		return entities.isEmpty();
	}

	public boolean contains(T o) {
		return entities.contains(o);
	}

	public boolean containsAll(Collection<? extends T> c) {
		return entities.containsAll(c);
	}

	public String toString() {
		return entities.toString();
	}

	public void clear() {
		entities.clear();
	}

	public boolean addAll(Collection<? extends T> c) {
		return entities.addAll(c);
	}
	
	public boolean addAll(GenericEntities<? extends T> e) {
		return this.addAll(e.entities);
	}

	public boolean removeAll(Collection<?> c) {
		return entities.removeAll(c);
	}
	
	
}
