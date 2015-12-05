package org.uu.lacpp15.g3.antcolony.erlang;

import java.io.IOException;
import java.net.InetAddress;

import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.IWorld;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.WorldBounds;

import com.ericsson.otp.erlang.OtpAuthException;
import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangAtom;
import com.ericsson.otp.erlang.OtpErlangDouble;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangExternalFun;
import com.ericsson.otp.erlang.OtpErlangFloat;
import com.ericsson.otp.erlang.OtpErlangInt;
import com.ericsson.otp.erlang.OtpErlangList;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangPid;
import com.ericsson.otp.erlang.OtpErlangTuple;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;

/**
 * Created by anders on 2015-12-03.
 */
public class ErlangSimulation implements ISimulation {

    private long nanoSecCounter;
    private SimpleErlangMap world;

    int nAnts = 1000;
    OtpConnection connection = null;

    public ErlangSimulation() throws IOException, OtpAuthException, OtpErlangExit {
        System.out.println("Simulation started ");

        int max = 100;
        world = new SimpleErlangMap(new WorldBounds(-max,max,-max,max), nAnts);
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
        int index = 0;
        while (index < nAnts) {
            OtpErlangList message = null;
            try {
                message = (OtpErlangList) connection.receive();
                for (OtpErlangObject obj :
                        message) {
                    OtpErlangTuple values = (OtpErlangTuple) obj;
                    OtpErlangDouble x = (OtpErlangDouble) values.elementAt(0);
                    OtpErlangDouble y = (OtpErlangDouble) values.elementAt(1);
                    double xPos = x.doubleValue();
                    double yPos = y.doubleValue();
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
