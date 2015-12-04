package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.common.AABoxInt2;
import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;

public class PheromoneGrid implements IRPheromoneGrid {
	
	private int				pad;
	private int				N;
	private FloatGrid		curGrid, nextGrid;
	private long			nanosBetweenBlurs;
	private long			nanosSinceBlur;
	private GaussianBlur	gb = new GaussianBlur();
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
	
	@Override
	public int getResolution() {
		return N - pad * 2;
	}
	
	@Override
	public float getGridValue(int x, int y) {
		return curGrid.get(x+pad, y+pad);
	}
	
	public void add(int x, int y, float amount) {
		final int res = getResolution();
		final int xIdx = (int)Math.round((x - worldBounds.xmin) / (double)(worldBounds.xmax - worldBounds.xmin) * res);
		final int yIdx = (int)Math.round((y - worldBounds.ymin) / (double)(worldBounds.ymax - worldBounds.ymin) * res);
		nextGrid.add(xIdx + pad, yIdx + pad, amount);
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
		nextGrid.clampFromAbove(1);
		FloatGrid temp = curGrid;
		curGrid = nextGrid;
		nextGrid = temp;
		nextGrid.setAll(0);
	}
	
}
