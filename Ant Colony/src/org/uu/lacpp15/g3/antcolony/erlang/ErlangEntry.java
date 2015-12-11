//package org.uu.lacpp15.g3.antcolony.erlang;
//
//import com.ericsson.otp.erlang.OtpErlangFloat;
//import com.ericsson.otp.erlang.OtpErlangList;
//import com.ericsson.otp.erlang.OtpErlangObject;
//import com.ericsson.otp.erlang.OtpErlangTuple;
//
//import java.util.Random;
//
///**
// * Created by anders on 2015-12-04.
// */
//public class ErlangEntry {
//
//
//    int x;
//    int y;
//    static Random rand = new Random();
//
//    public ErlangEntry(int x,int y){
//        this.x = x;
//        this.y = y;
//    }
//
//    public static ErlangEntry[] getRandom(int xMax,int yMax,int n){
//        ErlangEntry[] entrys= new ErlangEntry[n];
//        for (int i = 0; i < n; i++) {
//            entrys[i] = new ErlangEntry(rand.nextInt(xMax),rand.nextInt(yMax));
//            if (((entrys[i].getX()-xMax/2)*(entrys[i].getX()-xMax/2)) + ((entrys[i].getY()-yMax/2)*(entrys[i].getY()-yMax/2)) < 25*25){
//                i--;
//            }
//        }
//        return  entrys;
//    }
//
//    public static OtpErlangList entrysToList(ErlangEntry[] entrys){
//        OtpErlangObject[] erlangFood = new OtpErlangObject[2];
//        OtpErlangObject[] foodObjects = new OtpErlangObject[entrys.length];
//        for (int i = 0; i < entrys.length; i++) {
//            erlangFood[0] = new OtpErlangFloat(entrys[i].getX());
//            erlangFood[1] = new OtpErlangFloat(entrys[i].getY());
//            foodObjects[i] = new OtpErlangTuple(erlangFood);
//        }
//        return new OtpErlangList(foodObjects);
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//}
