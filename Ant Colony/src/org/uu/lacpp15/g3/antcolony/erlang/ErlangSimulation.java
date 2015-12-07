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

    int nAnts = 300;
    OtpConnection connection = null;

    public ErlangSimulation() throws IOException, OtpAuthException, OtpErlangExit {
        System.out.println("Simulation started ");
        int hiveOffset = 80;
        int max = 100;
        int start = 10;
        world = new SimpleErlangMap(new WorldBounds(0,max,0,max), nAnts,start,start,start+hiveOffset,start+hiveOffset);
        OtpSelf self = new OtpSelf("client");



        String name = "erlang@" + InetAddress.getLocalHost().getHostName();
        System.out.println(name);
        OtpPeer other  = new OtpPeer(name);
        connection = self.connect(other);



        OtpErlangExternalFun input = new OtpErlangExternalFun("main","run",0);
        OtpErlangList list = new OtpErlangList(input);

        System.out.println("Starting run ");
        connection.sendRPC("erlang","spawn",list);
        OtpErlangPid runPid = (OtpErlangPid) connection.receiveRPC();



        OtpErlangAtom atom = new OtpErlangAtom("masterPid");


        OtpErlangObject[] objects = new OtpErlangObject[2];
        objects[0] = atom;
        objects[1] = self.pid();
        System.out.println("recived run pid " + runPid);
        OtpErlangTuple send = new OtpErlangTuple(objects);
        connection.send(runPid,send);

        //Sends the number of ants
        atom = new OtpErlangAtom("nrAnts");
        OtpErlangInt nrAnts = new OtpErlangInt(nAnts);


        objects[0] = atom;
        objects[1] = nrAnts;
        send = new OtpErlangTuple(objects);
        connection.send(runPid,send);

        //Send world size
        atom = new OtpErlangAtom("worldMax");
        OtpErlangFloat worldSize = new OtpErlangFloat((float)max);


        objects[0] = atom;
        objects[1] = worldSize;
        send = new OtpErlangTuple(objects);
        connection.send(runPid,send);
        System.out.println("All messages sent ");

        atom = new OtpErlangAtom("nrActors");
        OtpErlangInt nrActors = new OtpErlangInt(10);
        objects[0] = atom;
        objects[1] = nrActors;
        System.out.println("recived run pid " + runPid);
        send = new OtpErlangTuple(objects);
        connection.send(runPid,send);


        atom = new OtpErlangAtom("start");
        objects[0] = atom;
        objects[1] = new OtpErlangInt(start);
        System.out.println("recived run pid " + runPid);
        send = new OtpErlangTuple(objects);
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
        while (index < nAnts) {
            OtpErlangList message = null;
            try {
                message = (OtpErlangList) connection.receive();
                for (OtpErlangObject obj :
                        message) {
                    OtpErlangTuple values = (OtpErlangTuple) obj;
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
            nanoSecCounter += nanoSecDelta;
    }

    @Override
    public IWorld getWorld() {
        return world;
    }

}
