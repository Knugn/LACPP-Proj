package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRFoodSources;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

public class Renderer {
	
	private Canvas canvas;
	//private BufferedImage image;
	private BufferedImage bgImage;
	
	public Renderer() {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(512,512));
		//setImageSize(512,512);
	}
	/*
	public BufferedImage getImage() {
		return image;
	}*/
	
	public Canvas getCanvas() {
		return canvas;
	}
	/*
	public void setImageSize(int width, int height) {
		this.image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	}*/
	
	public void render(ISimulation simulation) {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		
		final int w = canvas.getWidth();
		final int h = canvas.getHeight();
		
		renderBackground(simulation, g, w, h);
		
		renderEntities(simulation, g, w, h);
		
		g.dispose();
		
		bs.show();
	}
	
	private void renderBackground(ISimulation simulation, Graphics2D g, final int w, final int h) {
		IRPheromoneGrid hivePheroGrid = simulation.getWorld().getHivePheromoneGrid();
		IRPheromoneGrid foodPheroGrid = simulation.getWorld().getFoodPheromoneGrid();
		final int resx = hivePheroGrid.getResolutionX();
		final int resy = hivePheroGrid.getResolutionY();
		
		if (bgImage == null || bgImage.getWidth() != resx || bgImage.getHeight() != resy)
			bgImage = new BufferedImage(resx,resy,BufferedImage.TYPE_INT_RGB);
		final int[] pixels = ((DataBufferInt)(bgImage.getRaster().getDataBuffer())).getData();
		for (int y=0; y < resy; y++) {
			for (int x=0; x < resx; x++) {
				final float hiveGridValue = hivePheroGrid.getGridValue(x, y);
				final int hiveGridValueByte = (int)(hiveGridValue*255);
				final float foodGridValue = foodPheroGrid.getGridValue(x, y);
				final int foodGridValueByte = (int)(foodGridValue*255);
				pixels[y*resx+x] = 
						((0xFF-(hiveGridValueByte+foodGridValueByte)/2)<<8) 
						| (hiveGridValueByte << 16) 
						| foodGridValueByte;
			}
		}
		g.drawImage(bgImage, 0, 0, w, h, 0, 0, resx, resy, null);
	}

	private void renderEntities(ISimulation simulation, Graphics2D g, final int w, final int h) {
		IRAABoxInt2 bounds = simulation.getWorld().getBounds();
		final int xmin = bounds.getMinX();
		final int xmax = bounds.getMaxX();
		final int ymin = bounds.getMinY();
		final int ymax = bounds.getMaxY();
		
		IRHives hives = simulation.getWorld().getAllHives();
		if (hives != null) {
			g.setColor(Color.orange);
			IREntityIterator hiveIter = hives.iterator();
			while (hiveIter.next()) {
				final int x = toImageCoord(hiveIter.getx(), xmin, xmax, w);
				final int y = toImageCoord(hiveIter.gety(), ymin, ymax, h);
				final int d = (int)(w*hiveIter.getRadius()*2 / (xmax - xmin));
				g.fillOval(x - d/2, y - d/2, d, d);
			}
		}

		IRFoodSources foodSources = simulation.getWorld().getAllFoodSources();
		if (foodSources != null) {
			g.setColor(Color.white);
			IREntityIterator foodIter = foodSources.iterator();
			while (foodIter.next()) {
				final int x = toImageCoord(foodIter.getx(), xmin, xmax, w);
				final int y = toImageCoord(foodIter.gety(), ymin, ymax, h);
				final int d = (int)(w*foodIter.getRadius()*2 / (xmax - xmin));
				g.fillOval(x - d/2, y - d/2, d, d);
			}
		}
		
		IRAnts ants = simulation.getWorld().getAllAnts();
		if (ants != null) {
			g.setColor(Color.black);
			IREntityIterator antsIter = simulation.getWorld().getAllAnts().iterator();
			while (antsIter.next()) {
				int x = toImageCoord(antsIter.getx(), xmin, xmax, w);
				int y = toImageCoord(antsIter.gety(), ymin, ymax, h);
				int d = (int)(w*antsIter.getRadius()*2 / (xmax - xmin));
				g.fillRect(x - d/2, y - d/2, d, d);
			}
		}
	}
	
	private static int toImageCoord(int a, int amin, int amax, int imgSize) {
		return (int)((long)imgSize*(a-amin)/(amax-amin));
	}
	
}
