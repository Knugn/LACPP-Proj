package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;

public class Ants implements IRAnts {
	
	private Entities	entities;
	private long[]		ids;
	private int[]		foodCarried;
	
	public Ants(Entities entities, int nAnts) {
		this.entities = entities;
		this.ids = entities.allocMany(nAnts, null);
		this.foodCarried = new int[ids.length];
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
