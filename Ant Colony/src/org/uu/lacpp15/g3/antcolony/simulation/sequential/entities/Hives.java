package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

public class Hives extends GenericEntities<Hive> implements IRHives {

	public Hives(int nHives, float radius) {
		super(nHives);
		for (int i=0; i<nHives; i++)
			this.add(new Hive(0,0,radius));
	}
	
}
