package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import org.uu.lacpp15.g3.antcolony.common.IRAABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntityIterator;

public class EntityBatch {
	
	public static enum Shape {
		Rect, Circle
	}
	
	private Color	color;
	private Shape	shape;
	private IRAABoxInt2 bounds;
	private int[][]	XY	= new int[2][];
	private float[]	R;
	private int		idx	= 0;
	
	public EntityBatch(Color color, Shape shape, int nEntities, IRAABoxInt2 bounds) {
		this.color = color;
		this.shape = shape;
		this.bounds = bounds;
		for (int i = 0; i < XY.length; i++) {
			XY[i] = new int[nEntities];
		}
		R = new float[nEntities];
	}
	
	public EntityBatch(Color color, Shape shape, IRAABoxInt2 bounds, IREntities entities) {
		this(color, shape, entities.size(), bounds);
		IREntityIterator iter = entities.iterator();
		while (iter.next()) {
			add(iter.getx(), iter.gety(), iter.getRadius());
		}
	}
	
	public void add(int x, int y, float r) {
		XY[0][idx] = x;
		XY[1][idx] = y;
		R[idx] = r;
		idx++;
	}
	
	public void draw(Graphics2D g, int imgw, int imgh) {
		final int xmin = bounds.getMinX();
		final int xmax = bounds.getMaxX();
		final int ymin = bounds.getMinY();
		final int ymax = bounds.getMaxY();
		g.setColor(color);
		for (int i = 0; i < XY[0].length; i++) {
			final int d = (int)(imgw*R[i]*2 / (xmax - xmin));
			final int x = Renderer.toImageCoord(XY[0][i], xmin, xmax, imgw) - d/2;
			final int y = Renderer.toImageCoord(XY[1][i], ymin, ymax, imgh) - d/2;
			switch (shape) {
				case Rect:
					g.fillRect(x, y, d, d);
					break;
				case Circle:
					g.fillOval(x, y, d, d);
					break;
				default:
					throw new RuntimeException("Shape not implemented.");
			}
		}
	}
}
