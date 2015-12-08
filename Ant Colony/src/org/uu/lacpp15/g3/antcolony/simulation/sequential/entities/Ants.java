package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;

public class Ants extends GenericEntities<Ant> implements IRAnts {
	
	private float maxSpeed;
	
	public Ants(int nAnts, float radius, float maxSpeed) {
		super(nAnts);
		for (int i=0; i<nAnts; i++)
			this.add(new Ant(0,0,radius));
		setMaxSpeed(maxSpeed);
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		if (maxSpeed < 0)
			throw new IllegalArgumentException("maxSpeed must be non-negative.");
		this.maxSpeed = maxSpeed;
	}
	
	
	
}
