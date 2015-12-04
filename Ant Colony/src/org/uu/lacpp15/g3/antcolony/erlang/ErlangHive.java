package org.uu.lacpp15.g3.antcolony.erlang;

/**
 * Created by anders on 2015-12-04.
 */
public class ErlangHive {

    public ErlangHive(int xPos,int yPos){
        this.xPos = xPos;
        this.yPos = yPos;

    }

    int xPos;
    int yPos;

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
