package org.uu.lacpp15.g3.antcolony.client;

import java.util.ArrayList;
import java.util.List;

public class FpsLoop {
	
	public interface FpsListener {
		public void reportFps(int fps);
	}
	
	public interface FrameUpdater {
		public void update(long nanoSecDelta);
	}
	
	private static final long	NANOS_PER_SECOND	= 1000000000;
	
	private final float			fpsTarget;
	private boolean				stop;
	private long				fpsReportCounter;
	private int					frameCounter;
	
	private List<FpsListener>	fpsListeners		= new ArrayList<>(1);
	private List<FrameUpdater>	frameUpdaters		= new ArrayList<>(1);
	
	public FpsLoop() {
		this(60);
	}
	
	public FpsLoop(float fpsTarget) {
		if (fpsTarget <= 0)
			throw new IllegalArgumentException();
		this.fpsTarget = fpsTarget;
	}
	
	public void stop() {
		this.stop = true;
	}
	
	public void addFpsListener(FpsListener e) {
		if (e == null)
			throw new IllegalArgumentException();
		fpsListeners.add(e);
	}
	
	public void addFrameUpdater(FrameUpdater e) {
		if (e == null)
			throw new IllegalArgumentException();
		frameUpdaters.add(e);
	}
	
	public void runLoop()
	{
		long lastLoopTime = System.nanoTime();
		
		while (!stop)
		{
			long now = System.nanoTime();
			long nanoSecDelta = now - lastLoopTime;
			lastLoopTime = now;
			
			fpsReportCounter += nanoSecDelta;
			frameCounter++;
			
			// Report fps one time per seconds
			if (fpsReportCounter >= NANOS_PER_SECOND)
			{
				for (FpsListener fl : fpsListeners)
					fl.reportFps(frameCounter);
				fpsReportCounter -= NANOS_PER_SECOND;
				frameCounter = 0;
			}
			
			for (FrameUpdater fu : frameUpdaters)
				fu.update(nanoSecDelta);
			
			final long desiredFrameNanos = (long) (NANOS_PER_SECOND / fpsTarget);
			final long actualFameNanos = System.nanoTime() - lastLoopTime;
			final long sleepMillis = (desiredFrameNanos - actualFameNanos) / 1000000;
			if (sleepMillis > 0) {
				try {
					Thread.sleep(sleepMillis);
				}
				catch (InterruptedException e) {
					
				}
			}
		}
	}
}
