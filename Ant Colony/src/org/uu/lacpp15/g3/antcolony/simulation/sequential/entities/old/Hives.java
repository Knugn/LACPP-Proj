package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.old;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

public class Hives implements IRHives {
	
	private Entities entities;
	private long[] ids;
	private int[] foodStored;
	
	public Hives(float radius, Entities entities, int nHives) {
		if (radius < 0)
			throw new IllegalArgumentException("radius must be non-negative.");
		this.entities = entities;
		this.ids = entities.allocMany(nHives, null);
		for (long id : ids)
			entities.setRadius(id, radius);
		this.foodStored = new int[ids.length];
	}

	@Override
	public int size() {
		return ids.length;
	}

	@Override
	public HivesIterator iterator() {
		return this.new HivesIterator();
	}
	
	public class HivesIterator extends AEntityIterator {
		
		private int idIdx = -1;
		
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

		public int getFoodStored() {
			return foodStored[idIdx];
		}
		
		public void setFoodStored(int value) {
			if (value < 0)
				throw new IllegalArgumentException();
			foodStored[idIdx] = value;
		}

		@Override
		public float getRadius() {
			return entities.getRadius(ids[idIdx]);
		}

		@Override
		public void setRadius(float r) {
			entities.setRadius(ids[idIdx], r);
		}
	}
}
