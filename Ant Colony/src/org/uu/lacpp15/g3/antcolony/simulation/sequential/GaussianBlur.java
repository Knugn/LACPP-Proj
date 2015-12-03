package org.uu.lacpp15.g3.antcolony.simulation.sequential;

public class GaussianBlur {
	
	private float[] taps = {0.02275f,0.9545f,0.02275f};
	
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
	
	public static void main(String[] args) {
		final int N = 512; 
		FloatGrid src = new FloatGrid(N,N);
		FloatGrid dst = new FloatGrid(N,N);
		src.setAll(1);
		//src.set(2,2,5);
		//System.out.println(src);
		long t1 = System.nanoTime();
		GaussianBlur gb = new GaussianBlur();
		for (int i=0; i < 6000; i++) {
			gb.blur(src, dst, 1, 1);
			//System.out.println();
			//System.out.println(dst);
			src.setAll(0);
			FloatGrid temp = src;
			src = dst;
			dst = temp;
		}
		long t2 = System.nanoTime();
		System.out.println((t2-t1)/1000000 + "ms");
		
		/*
		gb.blur(dst, src, 1, 1);
		System.out.println();
		System.out.println(src);*/
	}
}
