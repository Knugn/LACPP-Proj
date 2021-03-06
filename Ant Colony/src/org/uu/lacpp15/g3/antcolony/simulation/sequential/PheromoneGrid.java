package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;

public class PheromoneGrid implements IRPheromoneGrid {
	
	private int				pad;
	private int				X, Y;
	private FloatGrid		curGrid;
	private WorldBounds		worldBounds;
	
	public PheromoneGrid(WorldBounds worldBounds) {
		this(128, 128, 1, 60);
		setWorldBounds(worldBounds);
	}
	
	private PheromoneGrid(int resx, int resy, int pad, int blurFreq) {
		this.pad = pad;
		this.X = resx + pad * 2;
		this.Y = resy + pad * 2;
		this.curGrid = new FloatGrid(X, Y);
	}
	
	public WorldBounds getWorldBounds() {
		return worldBounds;
	}
	
	public void setWorldBounds(WorldBounds worldBounds) {
		this.worldBounds = worldBounds;
	}
	
	@Override
	public int getResolutionX() {
		return X - pad * 2;
	}
	
	@Override
	public int getResolutionY() {
		return Y - pad * 2;
	}
	
	@Override
	public float getGridValue(int x, int y) {
		return curGrid.get(x + pad, y + pad);
	}
	
	public float getGridGradientX(int x, int y) {
		x += pad;
		y += pad;
		return 
				(-curGrid.get(x-1, y-1) + curGrid.get(x+1, y-1)) +
				(-curGrid.get(x-1, y) + curGrid.get(x+1, y))*2 +
				(-curGrid.get(x-1, y+1) + curGrid.get(x+1, y+1));
	}
	
	public float getGridGradientY(int x, int y) {
		x += pad;
		y += pad;
		return -(curGrid.get(x-1, y-1) + 2*curGrid.get(x, y-1) + curGrid.get(x+1, y-1))
				+(curGrid.get(x-1, y+1) + 2*curGrid.get(x, y+1) + curGrid.get(x+1, y+1));
	}
	
	public void dropPheromones(int x, int y, float strength) {
		final int xIdx = worldBounds.getChunkIndexX(x, getResolutionX());
		final int yIdx = worldBounds.getChunkIndexY(y, getResolutionY());
		curGrid.clampBelow(xIdx + pad, yIdx + pad, strength);
	}
	
	public void scalePheromones(float scale) {
		curGrid.scaleAll(scale);
	}
	
}
