package org.uu.lacpp15.g3.antcolony.erlang;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;

/**
 * Created by anders on 2015-12-03.
 */
public class ErlangAnts implements IRAnts  {

    ErlangAnt [] ants;
    int index;
    public ErlangAnts(int n){
        ants = new ErlangAnt[n];
        for(int i = 0; i < n; i++){
            ants[i] = new ErlangAnt(0,0);
        }
    }
    public void reset(){
        index = 0;
    }

    public void set(double x, double y){
        ants[index].setX(x);
        ants[index].setY(y);
        index++;
    }

    @Override
    public int size() {
        return ants.length;
    }



    @Override
    public IREntityIterator iterator() {
        return  new AntsIterator();
    }

    public class AntsIterator implements IREntityIterator {

        int	idx	= -1;
        int radiues = 1;

        @Override
        public boolean next() {
            return ++idx < ants.length;
        }

        @Override
        public int getCoord(int n) {
            if(n==0){
                return (int) ants[idx].getX();
            }
            else {
                return (int) ants[idx].getY();
            }
        }

        @Override
        public float getRadius() {
            return radiues;
        }


        @Override
        public long getId() {
            return index;
        }

        @Override
        public int getx() {
            return (int) ants[idx].getX();
        }

        @Override
        public int gety() {
            return (int) ants[idx].getY();
        }

    }
}
