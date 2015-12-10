package org.uu.lacpp15.g3.antcolony.simulation.sequential;

public class GaussianBlur {
	
	//private float[] taps = {0.02275f,0.9545f,0.02275f};
	private float[] taps = {0.000045f,0.9990f,0.000045f};
	
	public void blur(FloatGrid src, FloatGrid dst) {
		blur(src,dst,1,1);
	}
	
	public void blur(FloatGrid src, FloatGrid dst, int offx, int offy) {
		final int midw = src.getWidth()-taps.length+1;
		final int midh = src.getHeight();
		for (int y=0; y < midh; y++) {
			for (int x=0; x < midw; x++) {
				float sum = 0;
				for (int t=0; t < taps.length; t++)
					sum += src.get(x+t,y) * taps[t];
				final int tStart = Math.max(0, taps.length-1-y);
				final int tEnd = Math.min(taps.length, midh-y);
				for (int t=tStart; t < tEnd; t++) {
					dst.add(offx+x, offy+y-(taps.length-t-1), taps[t]*sum);
				}
			}
		}
	}
	
}
