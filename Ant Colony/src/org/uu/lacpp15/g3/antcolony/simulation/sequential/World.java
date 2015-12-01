package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.simulation.IWorld;

public class World implements IWorld {
	
	private Entities	entities;
	private Ants		ants;
	private Hives		hives;
	
	public World(int maxEntities, int nAnts, int nHives) {
		entities = new Entities(maxEntities);
		ants = new Ants(entities, nAnts);
		hives = new Hives(entities, nHives);
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
	
}
