package org.uu.lacpp15.g3.antcolony.simulation.sequential;

import java.util.Random;

import org.uu.lacpp15.g3.antcolony.simulation.sequential.Ants.AntsIterator;

public class AntsAI {
	
	private Random	random	= new Random();
	
	public void update(Ants ants, long nanoSecDelta) {
		int maxStep = (int) ((1 << (31 - 2)) * nanoSecDelta / 1000000000);
		AntsIterator antsIter = ants.iterator();
		while (antsIter.next()) {
			for (int n = 0; n < 2; n++)
				antsIter.setCoord(n, antsIter.getCoord(n) + random.nextInt(maxStep) - maxStep / 2);
		}
	}
}
