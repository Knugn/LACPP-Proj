package org.uu.lacpp15.g3.antcolony.erlang;

import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

/**
 * Created by anders on 2015-12-04.
 */
public class ErlangHives implements IRHives {

    private ErlangHive[] hives;


    public ErlangHives(ErlangHive[] hives){
        this.hives = hives;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public IREntityIterator iterator() {
         return  new HivesIterator();
    }

    public class HivesIterator implements IREntityIterator{

        int index = -1;

        @Override
        public boolean next() {
             return ++index < hives.length;
        }

        @Override
        public long getId() {
            return index;
        }

        @Override
        public int getx() {
            return getCoord(0);
        }

        @Override
        public int gety() {
            return getCoord(1);
        }

        @Override
        public int getCoord(int n) {
            if (n == 0){
                return hives[index].getxPos();
            }else {
                return hives[index].getyPos();
            }
        }

        @Override
        public float getRadius() {
            return 2;
        }
    }
}
