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
        return 1;
    }



    @Override
    public IREntityIterator iterator() {
        return  new AntsIterator();
    }

    public class AntsIterator extends AEntityIterator {

        int	idx	= -1;

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
            return 3;
        }

        @Override
        public void setCoord(int n, int value) {

        }



        @Override
        public long getId() {
            return index;
        }

    }
}
