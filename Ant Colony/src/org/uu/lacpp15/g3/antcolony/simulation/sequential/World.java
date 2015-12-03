package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.common.AABoxInt2;
import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.Ants.AntsIterator;

public class World implements IWorld {
	
	private AABoxInt2		bounds;
	private Entities	entities;
	private Ants		ants;
	private Hives		hives;
	
	public World(AABoxInt2 bounds, int maxEntities, int nAnts, int nHives) {
		if (bounds == null)
			throw new IllegalArgumentException("bounds must not be null.");
		this.bounds = bounds;
		this.entities = new Entities(maxEntities);
		final float maxSpeed = (bounds.xmax - bounds.xmin) / 20f; //cross the world in 20s  
		this.ants = new Ants(maxSpeed, entities, nAnts);
		this.hives = new Hives(entities, nHives);
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
	
	public void update(long nanoSecDelta) {
		AntsIterator antsIter = ants.iterator();
		while (antsIter.next()) {
			final int x = antsIter.getx();
			if (x < bounds.xmin)
				antsIter.setx(bounds.xmin);
			if (x > bounds.xmax)
				antsIter.setx(bounds.xmax);
			final int y = antsIter.gety();
			if (y < bounds.ymin)
				antsIter.sety(bounds.ymin);
			if (y > bounds.ymax)
				antsIter.sety(bounds.ymax);
		}
	}

}
