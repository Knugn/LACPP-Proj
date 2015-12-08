package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

import org.uu.lacpp15.g3.antcolony.simulation.entities.IRFoodSources;

public class FoodSources extends GenericEntities<FoodSource> implements IRFoodSources {

	public FoodSources(int maxFoodSources) {
		super(maxFoodSources);
	}
	
}
