package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
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
		IRAABoxInt2 bounds = simulation.getWorld().getBounds();
		final int xmin = bounds.getMinX();
		final int xmax = bounds.getMaxX();
		final int ymin = bounds.getMinY();
		final int ymax = bounds.getMaxY();
		while (antsIter.next()) {
			int x = toImageCoord(antsIter.getx(),xmin,xmax,w-2*r);
			int y = toImageCoord(antsIter.gety(),ymin,ymax,h-2*r);
			g.fillOval(x, y, r*2, r*2);
		}
		g.dispose();
	}
	
	private static int toImageCoord(int a, int amin, int amax, int imgSize) {
		return (int)Math.round((a-amin+0.5f)/(amax-amin)*imgSize);
	}
	
	private static int toImageCoord(int x, int w) {
		return (x >>(Integer.numberOfLeadingZeros(w)+1)) & (w-1);
	}
}
