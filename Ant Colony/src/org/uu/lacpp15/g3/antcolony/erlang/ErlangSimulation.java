package org.uu.lacpp15.g3.antcolony.erlang;

import java.io.IOException;
import java.net.InetAddress;

import com.ericsson.otp.erlang.*;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.WorldBounds;

/**
 * Created by anders on 2015-12-03.
 */
public class ErlangSimulation implements ISimulation {

    private long nanoSecCounter;
    private SimpleErlangMap world;
    OtpErlangPid runPid;
    int nAnts = 1000;
    OtpConnection connection = null;

    public ErlangSimulation() throws IOException, OtpAuthException, OtpErlangExit {
        System.out.println("Simulation started ");
        int max = 200;
        int hivePos = 200/2;
        int nrActors = 100;
        ErlangEntry[] food = ErlangEntry.getRandom(max,max,5);
        world = new SimpleErlangMap(new WorldBounds(0,max,0,max), nAnts,hivePos,hivePos, new ErlangFood(food));

        //Conect to server
        OtpSelf self = new OtpSelf("client");
        String name = "erlang@" + InetAddress.getLocalHost().getHostName();
        System.out.println(name);
        OtpPeer other  = new OtpPeer(name);
        connection = self.connect(other);

        //Starts main function
        OtpErlangExternalFun input = new OtpErlangExternalFun("main","run",0);
        OtpErlangList list = new OtpErlangList(input);
        System.out.println("Starting run ");
        connection.sendRPC("erlang","spawn",list);
        runPid = (OtpErlangPid) connection.receiveRPC();




        //Init server
        OtpErlangAtom atom = new OtpErlangAtom("init");
        OtpErlangObject[] objects = new OtpErlangObject[7];
        objects[0] = atom;
        objects[1] = self.pid();
        objects[2] = new OtpErlangInt(nAnts);
        objects[3] = new OtpErlangFloat((float)max);
        objects[4] = new OtpErlangInt(hivePos);
        objects[5] = ErlangEntry.entrysToList(food);
        objects[6] = new OtpErlangInt(nrActors);
        System.out.println("sending init " + runPid);
        OtpErlangTuple send = new OtpErlangTuple(objects);
        connection.send(runPid,send);
    }

    @Override
    public long getPassedNanoSec() {
        return nanoSecCounter;
    }

    @Override
    public void update(long nanoSecDelta) {
        world.reset();
        int index = 0;
        OtpErlangTuple values;
        OtpErlangTuple pos;

        OtpErlangList message = null;
        while (index < nAnts) {
            try {

                //Ants pos
                message = (OtpErlangList) connection.receive();
                for (OtpErlangObject obj :
                        message) {
                    values = (OtpErlangTuple) obj;
                    double xPos;
                    double yPos;

                    if (values.elementAt(0) instanceof OtpErlangDouble){
                        xPos  =   ((OtpErlangDouble) values.elementAt(0)).doubleValue();
                    }else{
                        xPos  =   ((OtpErlangLong) values.elementAt(0)).longValue();
                    }

                    if (values.elementAt(1) instanceof OtpErlangDouble){
                        yPos  =   ((OtpErlangDouble) values.elementAt(1)).doubleValue();
                    }else{
                        yPos  =   ((OtpErlangLong) values.elementAt(1)).longValue();
                    }

                    world.setAnt(xPos, yPos);

                    index++;
                }




            } catch (OtpErlangExit otpErlangExit) {
                otpErlangExit.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OtpAuthException e) {
                e.printStackTrace();
            }
        }



        pheromoneMessage();
        pheromoneMessage();

        try {
            connection.send(runPid, new OtpErlangAtom("done"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        nanoSecCounter += nanoSecDelta;
    }

    private void pheromoneMessage() {
        int pheromonLifeTime = 100;
        OtpErlangTuple message;
        OtpErlangTuple values;
        OtpErlangTuple pos;//Hive pheromon
        OtpErlangList list = null;
        OtpErlangAtom hive;
        boolean isHive = false;
        try {
            message = (OtpErlangTuple) connection.receive();
            hive = (OtpErlangAtom) message.elementAt(0);
            list = (OtpErlangList) message.elementAt(1);
            isHive = hive.booleanValue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OtpErlangExit otpErlangExit) {
            otpErlangExit.printStackTrace();
        } catch (OtpAuthException e) {
            e.printStackTrace();
        }
        for (OtpErlangObject obj :
                list) {
            values = (OtpErlangTuple) obj;
            int xPos;
            int yPos;
            pos = (OtpErlangTuple) values.elementAt(0);
            xPos  =   (int)((OtpErlangLong) pos.elementAt(0)).longValue();
            yPos  =   (int)((OtpErlangLong) pos.elementAt(1)).longValue();

            double strength;
            if (values.elementAt(2) instanceof OtpErlangDouble){
                strength  = pheromonLifeTime -  ((OtpErlangDouble) values.elementAt(2)).doubleValue();
            }else{
                strength  = pheromonLifeTime -   ((OtpErlangLong) values.elementAt(2)).longValue();
            }

            if (isHive) {
                world.addHivePheromon(xPos, yPos, (float) strength / pheromonLifeTime);
            }else{
                world.addFoodPheromon(xPos, yPos, (float) strength / pheromonLifeTime);
            }

        }
    }

    @Override
    public IWorld getWorld() {
        return world;
    }

}
