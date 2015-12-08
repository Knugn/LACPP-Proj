package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import java.util.Random;

import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Ants;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Entities;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.FoodSource;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.FoodSources;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.entities.Hives;

public class World implements IWorld {
	
	private WorldBounds	bounds;
	private Entities	allEntities;
	private Ants		ants;
	private Hives		hives;
	private FoodSources foodSources;
	
	private PheromoneGrid	hivePheromoneGrid, foodPheromoneGrid;
	
	public World(WorldBounds bounds, int maxEntities, int nAnts, int nHives, int nFoodSources) {
		if (bounds == null)
			throw new IllegalArgumentException("bounds must not be null.");
		
		this.bounds = bounds;
		
		this.allEntities = new Entities(maxEntities);
		final int w = bounds.xmax - bounds.xmin;
		final int h = bounds.ymax - bounds.ymin;
		final float maxSpeed = w / 20f; // cross the world in 20s
		final float antRadius = w / 256f;
		this.ants = new Ants(nAnts, antRadius, maxSpeed);
		this.hives = new Hives(nHives, antRadius * 16);
		this.foodSources = new FoodSources(nFoodSources);
		Random rand = new Random();
		for (int i=0; i<nFoodSources; i++) {
			this.foodSources.add(
					new FoodSource(
							bounds.xmin + rand.nextInt(w),
							bounds.ymin + rand.nextInt(h),
							antRadius*4,
							nAnts*5));

		}
		
		this.allEntities.addAll(ants);
		this.allEntities.addAll(hives);
		
		this.hivePheromoneGrid = new PheromoneGrid(bounds);
		this.foodPheromoneGrid = new PheromoneGrid(bounds);
	}
	
	@Override
	public WorldBounds getBounds() {
		return bounds;
	}
	
	@Override
	public Entities getAllEntities() {
		return allEntities;
	}
	
	@Override
	public Ants getAllAnts() {
		return ants;
	}
	
	@Override
	public Hives getAllHives() {
		return hives;
	}
	
	@Override
	public FoodSources getAllFoodSources() {
		return foodSources;
	}

	@Override
	public PheromoneGrid getHivePheromoneGrid() {
		return hivePheromoneGrid;
	}
	
	@Override
	public PheromoneGrid getFoodPheromoneGrid() {
		return foodPheromoneGrid;
	}

}
