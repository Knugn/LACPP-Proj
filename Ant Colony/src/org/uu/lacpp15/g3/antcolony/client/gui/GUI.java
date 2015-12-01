package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;

public class GUI {
	
	private Window			window;
	private InputHandler	inputHandler;
	private BufferedImage	worldImage;
	private Renderer		renderer;
	
	public GUI() {
		window = new Window("Ant Colony Simulation");
		inputHandler = new InputHandler();
		window.addKeyListener(inputHandler);
		window.getContentPane().setBackground(Color.gray);
		worldImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
		window.add(new JLabel(new ImageIcon(worldImage)));
		window.pack();
		window.setVisible(true);
		renderer = new Renderer();
	}
	
	public void render(ISimulation simulation) {
		renderer.render(simulation, worldImage);
		window.repaint();
	}
}
