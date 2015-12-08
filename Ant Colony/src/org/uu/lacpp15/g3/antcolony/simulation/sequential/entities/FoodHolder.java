package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

public class FoodHolder extends Entity {
	
	private int foodHeld;
	
	public FoodHolder() {
		this(0,0,0,0);
	}
	
	public FoodHolder(int x, int y, float r, int food) {
		super(x,y,r);
		setFoodHeld(food);
	}

	public int getFoodHeld() {
		return foodHeld;
	}

	public void setFoodHeld(int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("amount must be non-negative.");
		this.foodHeld = amount;
	}

	public void addFoodHeld(int amount) {
		setFoodHeld(getFoodHeld() + amount);
	}
	
	public boolean hasFood() {
		return foodHeld > 0;
	}
	
	public void takeFood(FoodHolder src, int amount) {
		amount = Math.min(amount, src.getFoodHeld());
		src.addFoodHeld(-amount);
		this.addFoodHeld(amount);
	}
	
	public void dropFood(FoodHolder dst) {
		dst.addFoodHeld(this.getFoodHeld());
		this.setFoodHeld(0);
	}
	
}
