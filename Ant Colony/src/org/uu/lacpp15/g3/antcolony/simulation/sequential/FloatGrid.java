package org.uu.lacpp15.g3.antcolony.simulation.sequential;

public class FloatGrid {
	
	private float[] grid;
	private int width, height;
	
	public FloatGrid(int width, int height) {
		if (width < 1 || height < 1)
			throw new IllegalArgumentException("width and height must both be greater than 0.");
		this.width = width;
		this.height = height;
		this.grid = new float[width*height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public float get(int x, int y) {
		return grid[y*width+x];
	}
	
	public void set(int x, int y, float value) {
		grid[y*width+x] = value;
	}
	
	public void add(int x, int y, float value) {
		grid[y*width+x] += value;
	}
	
	public void setAll(float value) {
		for (int i = 0; i < grid.length; i++) {
			grid[i] = value;
		}
	}
	
	public void clampBelow(int x, int y, float min) {
		final int idx = y*width+x;
		if (grid[idx] < min)
			grid[idx] = min;
	}
	
	public void clampAbove(int x, int y, float max) {
		final int idx = y*width+x;
		if (grid[idx] > max)
			grid[idx] = max;
	}
	
	public void clampAllFromBelow(float min) {
		for (int i = 0; i < grid.length; i++) {
			if (grid[i] < min)
			grid[i] = min;
		} 
	}

	public void clampAllFromAbove(float max) {
		for (int i = 0; i < grid.length; i++) {
			if (grid[i] > max)
			grid[i] = max;
		} 
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int iNewline = width;
		for (int i=0; i<grid.length; i++) {
			if (i == iNewline) {
				sb.append(System.lineSeparator());
				iNewline += width;
			}
			sb.append(String.format("%-10s ", grid[i]));
		}
		return sb.toString();
	}
	
}
