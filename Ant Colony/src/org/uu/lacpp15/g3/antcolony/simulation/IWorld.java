package org.uu.lacpp15.g3.antcolony.simulation;

import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

/**
 * Interface of the simulation world.
 * @author David
 *
 */
public interface IWorld {
	public IREntities getAllEntities();
	public IRAnts getAllAnts();
	public IRHives getAllHives();
}
