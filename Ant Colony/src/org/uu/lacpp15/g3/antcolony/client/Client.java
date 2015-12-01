package org.uu.lacpp15.g3.antcolony.client;

import org.uu.lacpp15.g3.antcolony.client.FpsLoop.FpsListener;
import org.uu.lacpp15.g3.antcolony.client.FpsLoop.FrameUpdater;
import org.uu.lacpp15.g3.antcolony.client.gui.GUI;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;

public class Client implements Runnable, FrameUpdater, FpsListener {

	private FpsLoop loop;
	private ISimulation simulation;
	private GUI gui;
	
	public Client() {
		this(new org.uu.lacpp15.g3.antcolony.simulation.sequential.Simulation());
	}
	
	public Client(ISimulation simulation) {
		gui = new GUI();
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
		gui.render(simulation);
	}
	
}
