package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Canvas;

public class SquareCanvas extends Canvas {
	
	private static final long	serialVersionUID	= 3473057836303739618L;

	@Override
	public void setBounds(int x, int y, int width, int height) {
		int min = Math.min(width, height);
		x += (width-min)/2;
		y += (height-min)/2;
		super.setBounds(x, y, min, min);
	}
}
