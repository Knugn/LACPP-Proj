package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import org.uu.lacpp15.g3.antcolony.common.AABoxInt2;

public class WorldBounds extends AABoxInt2 {

	public WorldBounds(int xmin, int xmax, int ymin, int ymax) {
		super(xmin, xmax, ymin, ymax);
	}
	
	public int getChunkIndex(int dim, int a, int res) {
		switch(dim) {
			case 0:
				return getChunkIndexX(a, res);
			case 1:
				return getChunkIndexY(a, res);
			default:
				throw new IllegalArgumentException("dim must be 0 or 1.");
		}
	}
	
	public int getChunkIndexX(int x, int res) {
		return (int)Math.round((x - xmin + 0.5) / (double)(xmax - xmin) * res);
	}
	
	public int getChunkIndexY(int y, int res) {
		return (int)Math.round((y - ymin + 0.5) / (double)(ymax - ymin) * res);
	}
	
}
