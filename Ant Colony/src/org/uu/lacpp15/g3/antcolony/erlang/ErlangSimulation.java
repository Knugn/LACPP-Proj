package org.uu.lacpp15.g3.antcolony.erlang;

import com.ericsson.otp.erlang.*;
import org.uu.lacpp15.g3.antcolony.common.AABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by anders on 2015-12-03.
 */
public class ErlangSimulation implements ISimulation {

    private long nanoSecCounter;
    private SimpleErlangMap world;
    private OtpMbox mBox = null;
    OtpErlangPid pid = null;
    int nAnts = 100;
    OtpConnection connection = null;

    public ErlangSimulation() throws IOException, OtpAuthException, OtpErlangExit {
        System.out.println("Simulation started ");

        int max = 100;
        world = new SimpleErlangMap(new AABoxInt2(-max,max,-max,max), nAnts);
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

        objects = new OtpErlangObject[2];
        objects[0] = atom;
        objects[1] = nrAnts;
        send = new OtpErlangTuple(objects);
        connection.send(runPid,send);

        //Send world size
        atom = new OtpErlangAtom("worldMax");
        OtpErlangFloat worldSize = new OtpErlangFloat((float)max);

        objects = new OtpErlangObject[2];
        objects[0] = atom;
        objects[1] = worldSize;
        send = new OtpErlangTuple(objects);
        connection.send(runPid,send);
        System.out.println("All messages sent ");







    }

    @Override
    public long getPassedNanoSec() {
        return nanoSecCounter;
    }

    @Override
    public void update(long nanoSecDelta) {
        world.reset();
       // System.out.println("Uppdating ");
        for(int i = 0; i < nAnts; i++){
            OtpErlangTuple message = null;
            try {
                message = (OtpErlangTuple) connection.receive();
                OtpErlangDouble x = (OtpErlangDouble) message.elementAt(0);
                OtpErlangDouble y = (OtpErlangDouble) message.elementAt(1);
                double xPos =x.doubleValue();
                double yPos =y.doubleValue();
                world.setAnt(xPos, yPos);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (OtpErlangExit otpErlangExit) {
                otpErlangExit.printStackTrace();
            } catch (OtpAuthException e) {
                e.printStackTrace();
            }
            //System.out.println("recived " + message);
        }

        nanoSecCounter += nanoSecDelta;
    }

    @Override
    public IWorld getWorld() {
        return world;
    }

}
