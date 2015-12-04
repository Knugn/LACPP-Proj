package org.uu.lacpp15.g3.antcolony.erlang;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

/**
 * Created by anders on 2015-12-03.
 */
public class SimpleErlangMap implements IWorld {

    ErlangAnts ants;
    ErlangEntrys entrys;
    IRAABoxInt2 box;
    ErlangHives hives;

    public SimpleErlangMap(IRAABoxInt2 box,int n){
        this.box = box;

        ants = new ErlangAnts(n);

        ErlangHive hive = new ErlangHive(5,5);
        ErlangHive[] hiveArray = new ErlangHive[1];
        hiveArray[0] = hive;
        hives = new ErlangHives(hiveArray);

        ErlangEntry entry = new ErlangEntry(8,8);
        ErlangEntry[] entryArray = new ErlangEntry[1];
        entryArray[0] = entry;
        entrys = new ErlangEntrys(entryArray);
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
        return entrys;
    }

    @Override
    public IRAnts getAllAnts() {
        return ants;
    }

    @Override
    public IRHives getAllHives() {
        return hives;
    }
}
