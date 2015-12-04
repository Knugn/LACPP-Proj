package org.uu.lacpp15.g3.antcolony.erlang;

/**
 * Created by anders on 2015-12-03.
 */
public class ErlangAnt {
    double x;
    double y;

    public ErlangAnt(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
