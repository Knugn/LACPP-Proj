package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import java.util.Random;

import org.uu.lacpp15.g3.antcolony.simulation.sequential.Ants.AntsIterator;

public class AntsAI {
	
	private Ants ants;
	private int[][] prevXY = new int[2][];
	private Random	random	= new Random();
	
	public AntsAI(Ants ants) {
		if (ants == null)
			throw new IllegalArgumentException();
		this.ants = ants;
		this.prevXY[0] = new int[ants.size()];
		this.prevXY[1] = new int[ants.size()];
		AntsIterator antsIter = ants.iterator();
		int idx = 0;
		while (antsIter.next()) {
			for (int n = 0; n < 2; n++) {
				prevXY[n][idx] = antsIter.getCoord(n);
			}
			idx++;
		}
	}
	
	public void update(long nanoSecDelta) {
		//int maxStep = (int) ((1 << (31 - 2)) * nanoSecDelta / 1000000000);
		double secDelta = nanoSecDelta / 1000000000.0;
		double frameMaxDelta = ants.getMaxSpeed()*secDelta;
		AntsIterator antsIter = ants.iterator();
		int idx = 0;
		while (antsIter.next()) {
			final int x = antsIter.getx();
			final int y = antsIter.gety();
			final int pdx = x - prevXY[0][idx];
			final int pdy = y - prevXY[1][idx];
			double ndx, ndy;
			if (pdx == 0 && pdy == 0) {
				double a = random.nextDouble()*2*Math.PI;
				ndx = Math.cos(a)*frameMaxDelta;
				ndy = Math.sin(a)*frameMaxDelta;
			}
			else {
				final double r = random.nextGaussian() / 10;
				ndx = pdx-pdy*r;
				ndy = pdy+pdx*r;
				double f = frameMaxDelta/Math.sqrt(ndx*ndx+ndy*ndy);
				ndx *= f;
				ndy *= f;
			}
			antsIter.setx(x+(int)ndx);
			antsIter.sety(y+(int)ndy);
			prevXY[0][idx] = x;
			prevXY[1][idx] = y;
			idx++;
		}
	}
}
