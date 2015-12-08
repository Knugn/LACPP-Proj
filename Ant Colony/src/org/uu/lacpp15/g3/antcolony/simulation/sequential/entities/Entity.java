package org.uu.lacpp15.g3.antcolony.simulation.sequential.entities;

import org.uu.lacpp15.g3.antcolony.common.Collision;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.WorldBounds;

public class Entity {
	
	private static long	nextId	= 1;
	
	final long			id		= nextId++;
	int					x, y;
	private float		r;
	
	public Entity() {
		this(0,0,0);
	}
	
	public Entity(int x, int y, float r) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public long getId() {
		return id;
	}
	
	public int getx() {
		return x;
	}
	
	public void setx(int x) {
		this.x = x;
	}
	
	public int gety() {
		return y;
	}
	
	public void sety(int y) {
		this.y = y;
	}
	
	public int getCoord(int dim) {
		switch (dim) {
			case 0:
				return x;
			case 1:
				return y;
			default:
				throw new IllegalArgumentException("dim must be 0 or 1.");
		}
	}
	
	public void setCoord(int dim, int value) {
		switch (dim) {
			case 0:
				this.x = value;
				return;
			case 1:
				this.y = value;
				return;
			default:
				throw new IllegalArgumentException("dim must be 0 or 1.");
		}
	}
	
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public float getRadius() {
		return r;
	}
	
	public void setRadius(float r) {
		if (r < 0)
			throw new IllegalArgumentException("radius must be non-negative.");
		this.r = r;
	}
	
	public void set(int x, int y, float r) {
		setx(x);
		sety(y);
		setRadius(r);
	}
	
	public void clamp(WorldBounds bounds) {
		if (x < bounds.xmin)
			x = bounds.xmin;
		if (x >= bounds.xmax)
			x = bounds.xmax-1;
		if (y < bounds.ymin)
			y = bounds.ymin;
		if (y >= bounds.ymax)
			y = bounds.ymax-1;
	}
	
	public boolean collides(Entity other) {
		return Collision.collides(x, y, r, other.x, other.y, other.r);
	}
	
}
