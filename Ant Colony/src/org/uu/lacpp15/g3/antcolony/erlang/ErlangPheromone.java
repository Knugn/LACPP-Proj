package org.uu.lacpp15.g3.antcolony.erlang;

import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.WorldBounds;

/**
 * Created by anders on 2015-12-08.
 */
public class ErlangPheromone implements IRPheromoneGrid {


    WorldBounds bounds;
    float box[][];

    public ErlangPheromone(WorldBounds bounds){
        this.bounds = bounds;
        box = new float[bounds.getMaxX() -bounds.getMinX()][bounds.getMaxY() -bounds.getMinY()];
    }


    public void restet() {
        for (int x = bounds.getMinX(); x < bounds.getMaxY(); x++) {
            for (int y = bounds.getMinY(); y < bounds.getMaxY(); y++) {
                box[x][y] = 0;
            }
        }
    }

    @Override
    public int getResolutionX() {
        return box.length;
    }


    @Override
    public int getResolutionY() {
        return box[0].length;
    }

    public void add(int x, int y, float strength){
        if (x < bounds.getMinX() || x >= bounds.getMaxX()  || y < bounds.getMinY() || y >= bounds.getMaxY() ){
            return;
        }
        box[x -bounds.getMinX()][y -bounds.getMinY()] = strength;
    }

    @Override
    public float getGridValue(int x, int y) {
        return box[x -bounds.getMinX()][y -bounds.getMinY()];
    }
}
