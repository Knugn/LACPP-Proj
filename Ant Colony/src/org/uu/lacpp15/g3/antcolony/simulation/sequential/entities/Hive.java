package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

public class Hive extends FoodHolder {

	public Hive() {
		this(0,0,1);
	}
	
	public Hive(int x, int y, float r) {
		super(x,y,r,0);
	}

}
