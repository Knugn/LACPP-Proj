package org.uu.lacpp15.g3.antcolony.common;

public class Collision {
	public static boolean collides(int x1, int y1, float r1, int x2, int y2, float r2) {
		long dx = x2-x1;
		long dy = y2-y1;
		float r = r1+r2;
		return dx*dx+dy*dy < r*r;
	}
}
