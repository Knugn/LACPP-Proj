package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

public class FoodSource extends FoodHolder {
	
	private int initialFoodHeld;
	
	public FoodSource(int x, int y, float r, int food) {
		super(x,y,r,food);
		initialFoodHeld = food;
	}
	
	@Override
	public float getRadius() {
		return super.getRadius()*getFoodHeld()/(float)initialFoodHeld;
	}
	
}
