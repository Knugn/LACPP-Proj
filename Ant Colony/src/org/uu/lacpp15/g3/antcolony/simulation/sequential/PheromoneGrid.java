package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.common.AABoxInt2;

public class PheromoneGrid {
	
	private int				pad;
	private int				N;
	private FloatGrid		curGrid, nextGrid;
	private long			nanosBetweenBlurs;
	private long			nanosSinceBlur;
	private GaussianBlur	gb;
	private AABoxInt2			worldBounds;
	
	public PheromoneGrid(AABoxInt2 worldBounds) {
		this(250, 3, 60);
		setWorldBounds(worldBounds);
	}
	
	private PheromoneGrid(int res, int pad, int blurFreq) {
		this.pad = pad;
		this.N = res + pad * 2;
		this.curGrid = new FloatGrid(N, N);
		this.nextGrid = new FloatGrid(N, N);
		this.nanosBetweenBlurs = 1000000000 / blurFreq;
		nanosSinceBlur = -nanosBetweenBlurs;
	}
	
	public AABoxInt2 getWorldBounds() {
		return worldBounds;
	}
	
	public void setWorldBounds(AABoxInt2 worldBounds) {
		this.worldBounds = worldBounds;
	}
	
	public int getResolution() {
		return N - pad * 2;
	}
	
	public void add(int x, int y, float amount) {
		nextGrid.add(x + pad, y + pad, amount);
	}
	
	public void update(long nanoSecDelta) {
		nanosSinceBlur += nanoSecDelta;
		while (nanosSinceBlur >= 0) {
			blur();
			nanosSinceBlur -= nanosBetweenBlurs;
		}
	}
	
	private void blur() {
		gb.blur(curGrid, nextGrid);
		FloatGrid temp = curGrid;
		curGrid = nextGrid;
		nextGrid = temp;
		nextGrid.setAll(0);
	}
	
}
