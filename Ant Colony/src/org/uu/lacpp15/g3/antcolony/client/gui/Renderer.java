package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.IRPheromoneGrid;

public class Renderer {
	
	private Canvas canvas;
	private BufferedImage image;
	
	public Renderer() {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(512,512));
		setImageSize(512,512);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void setImageSize(int width, int height) {
		this.image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	}
	
	public void render(ISimulation simulation) {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		
		final int w = canvas.getWidth();
		final int h = canvas.getHeight();
		g.clearRect(0, 0, w, h);
		g.setColor(Color.green);
		g.fillRect(0, 0, w, h);
		
		IRPheromoneGrid hivePheroGrid = simulation.getWorld().getHivePheromoneGrid();
		final int res = hivePheroGrid.getResolution();
		final BufferedImage hivePheroImage = new BufferedImage(res,res,BufferedImage.TYPE_INT_RGB);
		final int[] pixels = ((DataBufferInt)(hivePheroImage.getRaster().getDataBuffer())).getData();
		for (int y=0; y < res; y++) {
			for (int x=0; x < res; x++) {
				final float gridValue = hivePheroGrid.getGridValue(x, y);
				final int gridValueByte = (int)(gridValue*255);
				pixels[y*res+x] = ((0xFF-gridValueByte)<<8) | (gridValueByte << 16);
			}
		}
		g.drawImage(hivePheroImage, 0, 0, w, h, 0, 0, res, res, null);
		/*
		DataBufferInt buf = (DataBufferInt)image.getRaster().getDataBuffer();
		int[] pixels = buf.getData();
		for (int y=0; y < h; y++) {
			for (int x=0; x < w; x++) {
				final int xx = x *res / w;
				final int yy = y * res / h;
				final float gridValue = hivePheroGrid.getGridValue(xx, yy);
				pixels[y*w+x] |= ((int)(gridValue*255))<<16;
				//pixels[y*w+x] |= 0x00FF0000;
			}
		}*/
		
		IRAABoxInt2 bounds = simulation.getWorld().getBounds();
		final int xmin = bounds.getMinX();
		final int xmax = bounds.getMaxX();
		final int ymin = bounds.getMinY();
		final int ymax = bounds.getMaxY();
		
		g.setColor(Color.orange);
		IREntityIterator hiveIter = simulation.getWorld().getAllHives().iterator();
		while (hiveIter.next()) {
			int x = toImageCoord(hiveIter.getx(),xmin,xmax,w);
			int y = toImageCoord(hiveIter.gety(),ymin,ymax,h);
			int r = (int)Math.ceil(hiveIter.getRadius()/(xmax-xmin)*w);
			g.fillOval(x-r, y-r, r*2, r*2);
		}
		
		g.setColor(Color.black);
		IREntityIterator antsIter = simulation.getWorld().getAllAnts().iterator();
		while (antsIter.next()) {
			int x = toImageCoord(antsIter.getx(),xmin,xmax,w);
			int y = toImageCoord(antsIter.gety(),ymin,ymax,h);
			int r = (int)Math.ceil(antsIter.getRadius()/(xmax-xmin)*w);
			g.fillOval(x-r, y-r, r*2, r*2);
		}
		
		g.dispose();
		
		bs.show();
	}
	
	private static int toImageCoord(int a, int amin, int amax, int imgSize) {
		return (int)Math.round((a-amin+0.5f)/(amax-amin)*imgSize);
	}
	
}
