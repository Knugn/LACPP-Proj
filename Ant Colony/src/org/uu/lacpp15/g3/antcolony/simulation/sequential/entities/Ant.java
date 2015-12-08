package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

public class Ant extends FoodHolder {

	private float hivePheroDrop, foodPheroDrop;
	
	public Ant() {
		this(0,0,1);
	}
	
	public Ant(int x, int y, float r) {
		super(x, y, r, 0);
	}

	public float getHivePheroDrop() {
		return hivePheroDrop;
	}

	public void setHivePheroDrop(float hivePheroDrop) {
		if (hivePheroDrop < 0)
			throw new IllegalArgumentException("hivePheroDrop must be non-negative.");
		this.hivePheroDrop = hivePheroDrop;
	}
	
	public void scaleHivePheroDrop(float s) {
		if (s < 0)
			throw new IllegalArgumentException("s must non-negative.");
		this.hivePheroDrop *= s;
	}

	public float getFoodPheroDrop() {
		return foodPheroDrop;
	}

	public void setFoodPheroDrop(float foodPheroDrop) {
		if (foodPheroDrop < 0)
			throw new IllegalArgumentException("foodPheroDrop must be non-negative.");
		this.foodPheroDrop = foodPheroDrop;
	}
	
	public void scaleFoodPheroDrop(float s) {
		if (s < 0)
			throw new IllegalArgumentException("s must non-negative.");
		this.foodPheroDrop *= s;
	}

}
