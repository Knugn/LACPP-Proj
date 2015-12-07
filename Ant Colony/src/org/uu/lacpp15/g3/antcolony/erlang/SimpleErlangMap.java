package org.uu.lacpp15.g3.antcolony.erlang;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRFoodSources;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.PheromoneGrid;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.WorldBounds;

/**
 * Created by anders on 2015-12-03.
 */
public class SimpleErlangMap implements IWorld {

    ErlangAnts ants;
    ErlangFood food;
    WorldBounds box;
    ErlangHives hives;
    private PheromoneGrid hivePheromoneGrid;


    public SimpleErlangMap(WorldBounds box, int n,int hiveX,int hiveY,int foodX,int foodY){

        this.box = box;

        ants = new ErlangAnts(n);

        ErlangHive hive = new ErlangHive(hiveX,hiveY);
        ErlangHive[] hiveArray = new ErlangHive[1];
        hiveArray[0] = hive;
        hives = new ErlangHives(hiveArray);

        ErlangEntry entry = new ErlangEntry(foodX,foodY);
        ErlangEntry[] entryArray = new ErlangEntry[1];
        entryArray[0] = entry;
        food = new ErlangFood(entryArray);
        hivePheromoneGrid = new PheromoneGrid(box);
    }

    public void setAnt(double x, double y){
        ants.set(x,y);
    }

    public void reset(){
        ants.reset();
    }

    @Override
    public IRAABoxInt2 getBounds() {
        return box;
    }

    @Override
    public IREntities getAllEntities() {
        return null;
    }

    @Override
    public IRAnts getAllAnts() {
        return ants;
    }

    @Override
    public IRHives getAllHives() {
        return hives;
    }

    @Override
    public IRFoodSources getAllFoodSources() {
        return food;
    }

    @Override
    public IRPheromoneGrid getHivePheromoneGrid() {
        return hivePheromoneGrid;
    }
}
