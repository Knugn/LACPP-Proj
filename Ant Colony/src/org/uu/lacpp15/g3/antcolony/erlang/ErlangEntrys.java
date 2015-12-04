package org.uu.lacpp15.g3.antcolony.erlang;

import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;

/**
 * Created by anders on 2015-12-04.
 */
public class ErlangEntrys implements IREntities {

    private ErlangEntry[] entrys;


    public ErlangEntrys(ErlangEntry[] entrys){
        this.entrys = entrys;
    }


    @Override
    public int size() {
        return 1;
    }

    @Override
    public IREntityIterator iterator() {
        return new ErlangEntryIterator();
    }



    public class ErlangEntryIterator implements IREntityIterator{

        int index = -1;

        @Override
        public boolean next() {
            return ++index < entrys.length;
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
            if(n==0){
                return entrys[index].getX();
            }else {
                return  entrys[index].getY();
            }
        }
    }

}
