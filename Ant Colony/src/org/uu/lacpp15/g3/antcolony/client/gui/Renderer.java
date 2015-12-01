package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;

public class Renderer {
	
	public void render(ISimulation simulation, BufferedImage image) {
		Graphics g = image.getGraphics();
		final int w = image.getWidth();
		final int h = image.getHeight();
		g.clearRect(0, 0, w, h);
		g.setColor(Color.white);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.black);
		IREntityIterator antsIter = simulation.getWorld().getAllAnts().iterator();
		final int r = 5;
		while (antsIter.next()) {
			int x = toImageCoord(antsIter.getx(),w)-r;
			int y = toImageCoord(antsIter.gety(),h)-r;
			for (int i=-1; i<=1; i++)
				for (int j=-1; j<=1; j++)
					g.fillOval(x+i*w, y+j*h, r*2, r*2);
		}
		g.dispose();
	}
	
	private static int toImageCoord(int x, int w) {
		return (x >>(Integer.numberOfLeadingZeros(w)+1)) & (w-1);
	}
}
