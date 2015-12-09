package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRAnts;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRFoodSources;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IRHives;

public class Renderer {
	
	private class DrawCalls implements Runnable {
		
		private BufferStrategy	bs;
		private int imgw,imgh;
		private List<EntityBatch> entityBatches;
		
		public DrawCalls(BufferStrategy bs, int imgw, int imgh) {
			this.bs = bs;
			this.imgw = imgw;
			this.imgh = imgh;
			this.entityBatches = new ArrayList<>(3);
		}
		
		public void addEntityBatch(EntityBatch eb) {
			entityBatches.add(eb);
		}
		
		@Override
		public void run() {
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();
			g.drawImage(bgImage,
					0, 0, canvas.getWidth(), canvas.getHeight(),
					0, 0, bgImage.getWidth(), bgImage.getHeight(),
					null);
			for (EntityBatch eb : entityBatches)
				eb.draw(g, imgw, imgh);
			g.dispose();
			bs.show();
		}
		
	}
	
	private Canvas			canvas;
	private BufferedImage	bgImage;
	private EntityBatch		hiveBatch, foodBatch, antBatch;
	private ExecutorService drawExecutor;
	private Future<?> frameCompletion;
	
	public Renderer() {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(512, 512));
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void render(ISimulation simulation) {
		render(simulation, false);
		/*
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		final int w = canvas.getWidth();
		final int h = canvas.getHeight();
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		renderBackground(simulation, g, w, h);
		renderEntities(simulation, g, w, h);
		g.dispose();
		
		bs.show();
		*/
	}
	
	public void renderAsync(ISimulation simulation) {
		render(simulation, true);
	}
	
	public void render(ISimulation simulation, boolean async) {
		DrawCalls drawCalls = createDrawCalls(simulation);
		if (drawCalls == null)
			return;
		if (async) {
			if (drawExecutor == null)
				drawExecutor = Executors.newSingleThreadExecutor();
			frameCompletion = drawExecutor.submit(drawCalls);
			return;
		}
		drawCalls.run();
	}
	
	public DrawCalls createDrawCalls(ISimulation simulation) {
		awaitFrameCompletion();
		
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(2);
			return null;
		}
		final int w = canvas.getWidth();
		final int h = canvas.getHeight();
		
		DrawCalls drawCalls = new DrawCalls(bs,w,h);
		renderBackground(simulation, null, w, h);
		
		IRAABoxInt2 bounds = simulation.getWorld().getBounds();
		IREntities hives = simulation.getWorld().getAllHives();
		if (hives != null) {
			if (hiveBatch == null)
				hiveBatch = new EntityBatch(Color.orange, EntityBatch.Shape.Circle);
			hiveBatch.set(bounds, hives);
			drawCalls.addEntityBatch(hiveBatch);
		}
		IREntities food = simulation.getWorld().getAllFoodSources();
		if (food != null) {
			if (foodBatch == null)
				foodBatch = new EntityBatch(Color.white, EntityBatch.Shape.Circle);
			foodBatch.set(bounds,food);
			drawCalls.addEntityBatch(foodBatch);
		}
		IREntities ants = simulation.getWorld().getAllAnts();
		if (ants != null) {
			if (antBatch == null)
				antBatch = new EntityBatch(Color.black, EntityBatch.Shape.Rect);
			antBatch.set(bounds, ants);
			drawCalls.addEntityBatch(antBatch);
		}
		
		return drawCalls;
	}
	
	public boolean isFrameComplete() {
		if (frameCompletion == null)
			return true;
		return frameCompletion.isDone();
	}
	
	public void awaitFrameCompletion() {
		if (frameCompletion != null) {
			try {
				frameCompletion.get();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			frameCompletion = null;
		}
	}
	
	private void renderBackground(ISimulation simulation, Graphics2D g, final int w, final int h) {
		IRPheromoneGrid hivePheroGrid = simulation.getWorld().getHivePheromoneGrid();
		IRPheromoneGrid foodPheroGrid = simulation.getWorld().getFoodPheromoneGrid();
		final int resx = hivePheroGrid.getResolutionX();
		final int resy = hivePheroGrid.getResolutionY();
		
		if (bgImage == null || bgImage.getWidth() != resx || bgImage.getHeight() != resy)
			bgImage = new BufferedImage(resx, resy, BufferedImage.TYPE_INT_RGB);
		final int[] pixels = ((DataBufferInt) (bgImage.getRaster().getDataBuffer())).getData();
		for (int y = 0; y < resy; y++) {
			for (int x = 0; x < resx; x++) {
				final float hiveGridValue = hivePheroGrid.getGridValue(x, y);
				final int hiveGridValueByte = (int) (hiveGridValue * 255);
				final float foodGridValue = foodPheroGrid.getGridValue(x, y);
				final int foodGridValueByte = (int) (foodGridValue * 255);
				pixels[y * resx + x] =
						((0xFF - (hiveGridValueByte + foodGridValueByte) / 2) << 8)
								| (hiveGridValueByte << 16)
								| foodGridValueByte;
			}
		}
		if (g != null)
			g.drawImage(bgImage, 0, 0, w, h, 0, 0, resx, resy, null);
	}
	
	@Deprecated
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
				final int d = (int) (w * hiveIter.getRadius() * 2 / (xmax - xmin));
				g.fillOval(x - d / 2, y - d / 2, d, d);
			}
		}
		
		IRFoodSources foodSources = simulation.getWorld().getAllFoodSources();
		if (foodSources != null) {
			g.setColor(Color.white);
			IREntityIterator foodIter = foodSources.iterator();
			while (foodIter.next()) {
				final int x = toImageCoord(foodIter.getx(), xmin, xmax, w);
				final int y = toImageCoord(foodIter.gety(), ymin, ymax, h);
				final int d = (int) (w * foodIter.getRadius() * 2 / (xmax - xmin));
				g.fillOval(x - d / 2, y - d / 2, d, d);
			}
		}
		
		IRAnts ants = simulation.getWorld().getAllAnts();
		if (ants != null) {
			g.setColor(Color.black);
			IREntityIterator antsIter = simulation.getWorld().getAllAnts().iterator();
			while (antsIter.next()) {
				int x = toImageCoord(antsIter.getx(), xmin, xmax, w);
				int y = toImageCoord(antsIter.gety(), ymin, ymax, h);
				int d = (int) (w * antsIter.getRadius() * 2 / (xmax - xmin));
				g.fillRect(x - d / 2, y - d / 2, d, d);
			}
		}
	}
	
	public static int toImageCoord(int a, int amin, int amax, int imgSize) {
		return (int) ((long) imgSize * (a - amin) / (amax - amin));
	}
	
}
