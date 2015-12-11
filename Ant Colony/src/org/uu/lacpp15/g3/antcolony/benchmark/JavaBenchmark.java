package org.uu.lacpp15.g3.antcolony.benchmark;

import java.util.ArrayList;

import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.sequential.Simulation;

public class JavaBenchmark {
	
	public static void main(String[] args) {
		long nanoSecDelta = 1000000000 / 60;
		// long maxNanosRunning = (long)1000000000*60*10;
		long reportInterval = (long) 1000000000 * 10;
		int nAnts = 10000, nFood = 10;
		for (int i = 1; i < args.length; i++) {
			String[] splitArg = args[i].split("=");
			if (splitArg.length == 2) {
				String val = splitArg[1];
				switch (splitArg[0].toLowerCase()) {
					case "nants":
					case "numants":
						nAnts = Integer.parseInt(val);
						break;
					case "nfood":
					case "numfood":
						nFood = Integer.parseInt(val);
						break;
					case "reportinterval":
						reportInterval = (long) Integer.parseInt(val) * 1000000000;
						break;
					default:
						throw new IllegalArgumentException("unknown input argument");
				}
			}
		}
		System.out.println("Running java ant colony simulation benchmark with " + nAnts + " and " + nFood + " food sources.");
		System.out.println("Report interval: " + reportInterval / 1000000000 + "s");
		System.out.println();
		ISimulation simulation = new Simulation(nAnts, nFood);
		ArrayList<Long> timeSamples = new ArrayList<>(100000);
		timeSamples.add(System.nanoTime());
		int lastReportIdx = 0;
		while (true) {
			simulation.update(nanoSecDelta);
			long now = System.nanoTime();
			timeSamples.add(now);
			if (now - timeSamples.get(lastReportIdx) >= reportInterval) {
				int nFrames = (timeSamples.size() - lastReportIdx);
				long avg = (now - timeSamples.get(lastReportIdx)) / nFrames;
				long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
				for (int i = lastReportIdx + 1; i < timeSamples.size(); i++) {
					long delta = timeSamples.get(i) - timeSamples.get(i - 1);
					if (min > delta)
						min = delta;
					if (max < delta)
						max = delta;
				}
				String br = System.lineSeparator();
				StringBuilder sb = new StringBuilder();
				sb.append("Benchmark report for last " + (reportInterval / 1000000000) + "s.");
				sb.append(br);
				sb.append("Number of frames: " + nFrames);
				sb.append(br);
				sb.append("Avarage frame time: " + avg + "ns");
				sb.append(br);
				sb.append("Minimum frame time: " + min + "ns");
				sb.append(br);
				sb.append("Maximum frame time: " + max + "ns");
				sb.append(br);
				System.out.println(sb.toString());
				lastReportIdx = timeSamples.size() - 1;
			}
		} // while (timeSamples.get(timeSamples.size()-1)-timeSamples.get(0) < maxNanosRunning);
	}
	
}
