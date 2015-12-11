package org.uu.lacpp15.g3.antcolony.erlang;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRFoodSources;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.WorldBounds;

/**
 * Created by anders on 2015-12-03.
 */
public class SimpleErlangMap implements IWorld {

    private ErlangAnts ants;
    private ErlangFood food;
    private WorldBounds box;
    private ErlangHives hives;
    private ErlangPheromone hivePheromoneGrid;
    private ErlangPheromone foodPheromone;


    public SimpleErlangMap(WorldBounds box, int n,int hiveX,int hiveY,ErlangFood food){

        this.box = box;

        ants = new ErlangAnts(n);

        ErlangHive hive = new ErlangHive(hiveX,hiveY);
        ErlangHive[] hiveArray = new ErlangHive[1];
        hiveArray[0] = hive;
        hives = new ErlangHives(hiveArray);


        this.food = food;
        hivePheromoneGrid = new ErlangPheromone(box);
        foodPheromone = new ErlangPheromone(box);
    }

    public void setAnt(double x, double y){
        ants.set(x,y);
    }

    public void reset(){
        ants.reset();
        hivePheromoneGrid.restet();
        foodPheromone.restet();
    }

    public void addFoodPheromon(int x,int y,float strength){
        foodPheromone.add(x,y,strength);
    }

    public void addHivePheromon(int x,int y,float strength){
        hivePheromoneGrid.add(x,y,strength);
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

    @Override
    public IRPheromoneGrid getFoodPheromoneGrid() {
        return foodPheromone;
    }
}
