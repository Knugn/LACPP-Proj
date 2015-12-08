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

    int nAnts = 500;
    OtpConnection connection = null;

    public ErlangSimulation() throws IOException, OtpAuthException, OtpErlangExit {
        System.out.println("Simulation started ");
        int hiveOffset = 80;
        int max = 200;
        int hivePos = 10;
        int nrActors = 100;
        int foodX = 180;
        int foodY = 150;
        world = new SimpleErlangMap(new WorldBounds(0,max,0,max), nAnts,hivePos,hivePos,foodX,foodY);

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
        OtpErlangPid runPid = (OtpErlangPid) connection.receiveRPC();

        //Init server
        OtpErlangAtom atom = new OtpErlangAtom("init");
        OtpErlangObject[] objects = new OtpErlangObject[8];
        objects[0] = atom;
        objects[1] = self.pid();
        objects[2] = new OtpErlangInt(nAnts);
        objects[3] = new OtpErlangFloat((float)max);
        objects[4] = new OtpErlangInt(hivePos);
        objects[5] = new OtpErlangInt(foodX);
        objects[6] = new OtpErlangInt(foodY);
        objects[7] = new OtpErlangInt(nrActors);
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
        OtpErlangList message;
        while (index < nAnts) {
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
