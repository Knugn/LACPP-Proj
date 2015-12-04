package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.common.AABoxInt2;
import org.uu.lacpp15.g3.antcolony.common.Collision;
import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.Ants.AntsIterator;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.Hives.HivesIterator;

public class World implements IWorld {
	
	private AABoxInt2	bounds;
	private Entities	entities;
	private Ants		ants;
	private Hives		hives;
	
	private PheromoneGrid	hivePheromoneGrid, foodPheromoneGrid;
	
	public World(AABoxInt2 bounds, int maxEntities, int nAnts, int nHives) {
		if (bounds == null)
			throw new IllegalArgumentException("bounds must not be null.");
		this.bounds = bounds;
		this.entities = new Entities(maxEntities);
		final int w = bounds.xmax - bounds.xmin;
		final float maxSpeed = w / 20f; // cross the world in 20s
		final float antRadius = w / 256f;
		this.ants = new Ants(maxSpeed, antRadius, entities, nAnts);
		this.hives = new Hives(antRadius * 16, entities, nHives);
		this.hivePheromoneGrid = new PheromoneGrid(bounds);
	}
	
	@Override
	public IRAABoxInt2 getBounds() {
		return bounds;
	}
	
	@Override
	public Entities getAllEntities() {
		return entities;
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
	public IRPheromoneGrid getHivePheromoneGrid() {
		return hivePheromoneGrid;
	}
	
	public void update(long nanoSecDelta) {
		AntsIterator antsIter = ants.iterator();
		while (antsIter.next()) {
			// clamp the ants inside boundaries
			final int x = antsIter.getx();
			if (x < bounds.xmin)
				antsIter.setx(bounds.xmin);
			if (x >= bounds.xmax)
				antsIter.setx(bounds.xmax-1);
			final int y = antsIter.gety();
			if (y < bounds.ymin)
				antsIter.sety(bounds.ymin);
			if (y >= bounds.ymax)
				antsIter.sety(bounds.ymax-1);
			
			HivesIterator hiveIter = hives.iterator();
			while (hiveIter.next()) {
				if (Collision.collides(
						antsIter.getx(), antsIter.gety(), antsIter.getRadius(),
						hiveIter.getx(), hiveIter.gety(), hiveIter.getRadius())) {
					antsIter.setNanosSinceHive(0);
				}
				else {
					antsIter.setNanosSinceHive(antsIter.getNanosSinceHive() + nanoSecDelta);
				}
			}
			float hivePheroDrop = Math.max(0, 0.1f*(1-antsIter.getNanosSinceHive()/1000000000 / 10f));
			hivePheromoneGrid.add(antsIter.getx(), antsIter.gety(), hivePheroDrop);
		}
		hivePheromoneGrid.update(nanoSecDelta);
	}

}
