package org.uu.lacpp15.g3.antcolony.simulation;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

/**
 * Interface of the simulation world.
 * @author David
 *
 */
public interface IWorld {
	public IRAABoxInt2 getBounds();
	public IREntities getAllEntities();
	public IRAnts getAllAnts();
	public IRHives getAllHives();
}
