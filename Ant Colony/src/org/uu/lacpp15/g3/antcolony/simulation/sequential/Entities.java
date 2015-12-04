package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.uu.lacpp15.g3.antcolony.simulation.entities.AEntityIterator;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IWEntities;

public class Entities implements IWEntities {
	
	private int[][]					XY	= new int[2][];
	private float[]					R;
	private int						nextIdx;
	private long					nextId;
	private TreeMap<Long, Integer>	idToIdxMap;
	
	public Entities(int maxEntities) {
		for (int i = 0; i < XY.length; i++)
			XY[i] = new int[maxEntities];
		R = new float[maxEntities];
		idToIdxMap = new TreeMap<>();
	}
	
	public long alloc() {
		return alloc(0, 0, 0);
	}
	
	public long alloc(int x, int y, float r) {
		if (size() >= XY[0].length)
			throw new RuntimeException("Max entities already allocated.");
		long id = nextId++;
		int idx = getFreeIndex();
		idToIdxMap.put(id, idx);
		XY[0][idx] = x;
		XY[1][idx] = y;
		R[idx] = r;
		return id;
	}
	
	public long[] allocMany(int n, long[] dst) {
		if (dst == null)
			dst = new long[n];
		allocMany(n, dst, 0);
		return dst;
	}
	
	public void allocMany(int n, long[] dst, int offset) {
		for (int i = 0; i < n; i++)
			dst[offset++] = alloc();
	}
	
	private int getFreeIndex() {
		if (nextIdx >= XY[0].length)
			defrag();
		return nextIdx++;
	}
	
	private void defrag() {
		// TODO:
		throw new RuntimeException("Not implemented.");
	}
	
	public boolean free(long id) {
		return free(id, false);
	}
	
	public boolean free(long id, boolean ignoreAlreadyFreed) {
		if (!idToIdxMap.containsKey(id)) {
			if (ignoreAlreadyFreed)
				return false;
			throw new IllegalArgumentException("No entity allocated for id " + id + ".");
		}
		idToIdxMap.remove(id);
		return true;
	}
	
	public int getx(long id) {
		return getCoord(id, 0);
	}
	
	public int gety(long id) {
		return getCoord(id, 1);
	}
	
	public int getCoord(long id, int n) {
		int idx = getIndex(id);
		return XY[n][idx];
	}
	
	public void setx(long id, int x) {
		setCoord(id, 0, x);
	}
	
	public void sety(long id, int y) {
		setCoord(id, 1, y);
	}
	
	public void setCoord(long id, int n, int value) {
		int idx = getIndex(id);
		XY[n][idx] = value;
	}
	
	public void setCoords(long id, int x, int y) {
		int idx = getIndex(id);
		XY[0][idx] = x;
		XY[1][idx] = y;
	}
	
	public float getRadius(long id) {
		int idx = getIndex(id);
		return R[idx];
	}
	
	public void setRadius(long id, float radius) {
		if (radius < 0)
			throw new IllegalArgumentException("radius must be non-negative.");
		int idx = getIndex(id);
		R[idx] = radius;
	}
	
	private int getIndex(long id) {
		if (!idToIdxMap.containsKey(id))
			throw new IllegalArgumentException("No entity allocated for id " + id + ".");
		return idToIdxMap.get(id);
	}
	
	@Override
	public int size() {
		return idToIdxMap.size();
	}
	
	@Override
	public EntityIterator iterator() {
		return this.new EntityIterator();
	}
	
	private class EntityIterator extends AEntityIterator {
		
		Iterator<Entry<Long, Integer>>	entries;
		Entry<Long, Integer>			cur;
		
		EntityIterator() {
			entries = idToIdxMap.entrySet().iterator();
		}
		
		@Override
		public boolean next() {
			if (entries.hasNext()) {
				cur = entries.next();
				return true;
			}
			return false;
		}

		@Override
		public long getId() {
			return cur.getKey();
		}
		
		@Override
		public int getCoord(int n) {
			return XY[n][cur.getValue()];
		}
		
		@Override
		public void setCoord(int n, int value) {
			XY[n][cur.getValue()] = value;
		}

		@Override
		public float getRadius() {
			return R[cur.getValue()];
		}
		
	}
}
