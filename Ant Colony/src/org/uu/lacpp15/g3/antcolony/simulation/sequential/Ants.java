package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;

public class Ants implements IRAnts {
	
	private float maxSpeed;
	
	private Entities	entities;
	private long[]		ids;
	private int[]		foodCarried;
	
	public Ants(float maxSpeed, Entities entities, int nAnts) {
		if (maxSpeed < 0)
			throw new IllegalArgumentException("maxSpeed must be non-negative.");
		this.maxSpeed = maxSpeed;
		this.entities = entities;
		this.ids = entities.allocMany(nAnts, null);
		this.foodCarried = new int[ids.length];
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	@Override
	public int size() {
		return ids.length;
	}
	
	@Override
	public AntsIterator iterator() {
		return this.new AntsIterator();
	}
	
	public class AntsIterator extends AEntityIterator {
		
		int	idIdx	= -1;
		
		@Override
		public boolean next() {
			return ++idIdx < ids.length;
		}
		
		@Override
		public int getCoord(int n) {
			return entities.getCoord(ids[idIdx], n);
		}
		
		@Override
		public void setCoord(int n, int value) {
			entities.setCoord(ids[idIdx], n, value);
		}
		
		public int getFoodCarried() {
			return foodCarried[idIdx];
		}
		
		public void setFoodCarried(int value) {
			if (value < 0)
				throw new IllegalArgumentException();
			foodCarried[idIdx] = value;
		}
		
		@Override
		public long getId() {
			return ids[idIdx];
		}
		
	}
	
}
