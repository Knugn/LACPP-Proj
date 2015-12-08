package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.old;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;

public class Ants implements IRAnts {
	
	private final float maxSpeed;
	private final float radius;
	
	private Entities	entities;
	private long[]		ids;
	private int[]		foodCarried;
	private long[]		nanosSinceHive;
	
	public Ants(float maxSpeed, float radius, Entities entities, int nAnts) {
		if (maxSpeed < 0)
			throw new IllegalArgumentException("maxSpeed must be non-negative.");
		if (radius < 0)
			throw new IllegalArgumentException("raidus must be non-negative.");
		this.maxSpeed = maxSpeed;
		this.radius = radius;
		this.entities = entities;
		this.ids = entities.allocMany(nAnts, null);
		for (long id: ids)
			entities.setRadius(id, radius);
		this.foodCarried = new int[ids.length];
		this.nanosSinceHive = new long[ids.length];
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
		public long getId() {
			return ids[idIdx];
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
		public float getRadius() {
			// All ants have same radius
			return radius;
		}

		@Override
		public void setRadius(float r) {
			throw new RuntimeException("Setting ant radius is not supported.");
		}
		
		public long getNanosSinceHive() {
			return nanosSinceHive[idIdx];
		}
		
		public void setNanosSinceHive(long nanos) {
			nanosSinceHive[idIdx] = nanos;
		}

	}
	
}
