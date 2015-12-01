package org.uu.lacpp15.g3.antcolony.client;

import org.uu.lacpp15.g3.antcolony.client.FpsLoop.FpsListener;
import org.uu.lacpp15.g3.antcolony.client.FpsLoop.FrameUpdater;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;

public class Client implements Runnable, FrameUpdater, FpsListener {

	FpsLoop loop;
	ISimulation simulation;
	
	public Client(ISimulation simulation) {
		setSimulation(simulation);
	}
	
	public ISimulation getSimulation() {
		return simulation;
	}

	public void setSimulation(ISimulation simulation) {
		this.simulation = simulation;
	}

	@Override
	public void run() {
		loop = new FpsLoop();
		loop.addFpsListener(this);
		loop.addFrameUpdater(this);
		loop.runLoop();
	}

	@Override
	public void reportFps(int fps) {
		System.out.println("FPS: "+fps);
	}

	@Override
	public void update(long nanoSecDelta) {
		simulation.update(nanoSecDelta);
		//TODO: render
	}
	
}
