package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;

public class GUI {
	
	private Window			window;
	private InputHandler	inputHandler;
	private Renderer		renderer;
	private SimulationInfoPanel simulationInfoPanel;
	
	public GUI() {
		window = new Window("Ant Colony Simulation");
		inputHandler = new InputHandler();
		window.addKeyListener(inputHandler);
		renderer = new Renderer();
		window.add(renderer.getCanvas(), createCanvasConstrains());
		addSidePanel();
		window.setIgnoreRepaint(true);
		window.pack();
		window.setVisible(true);
	}
	
	public void render(ISimulation simulation) {
		if (renderer.isFrameComplete())
			renderer.renderAsync(simulation);
		simulationInfoPanel.updateInfo(simulation);
	}
	
	private void addSidePanel() {
		JPanel sidePanel = new JPanel();
		sidePanel.add(simulationInfoPanel=new SimulationInfoPanel());
		window.add(sidePanel, createSidePanelConstrains());
	}
	
	private static GridBagConstraints createCanvasConstrains() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		return gbc;
	}
	
	private static GridBagConstraints createSidePanelConstrains() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = 0;
		gbc.weighty = 1;
		return gbc;
	}
}
