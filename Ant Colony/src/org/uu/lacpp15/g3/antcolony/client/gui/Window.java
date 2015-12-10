package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Window extends JFrame implements FocusListener {
	
	private static final long	serialVersionUID	= -1225770982254348884L;
	private GraphicsDevice		screen				= null;
	private boolean				isFullScreen		= false;
	
	public Window(String title) {
		super(title);
		this.addFocusListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});
		getContentPane().setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.black);
		setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
	}
	
	public void setFullScreen(boolean fullscreen) {
		if (fullscreen ^ isFullScreen) {
			if (fullscreen) {
				goFullScreen();
			}
			else {
				restoreScreen();
			}
		}
	}
	
	private void goFullScreen() {
		setUndecorated(true);
		setResizable(false);
		screen = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		screen.setFullScreenWindow(this);
		if (screen.isDisplayChangeSupported()) {
			screen.setDisplayMode(screen.getDisplayMode());
		}
		isFullScreen = true;
	}
	
	private void restoreScreen() {
		setUndecorated(false);
		setResizable(true);
		screen.setFullScreenWindow(null);
		isFullScreen = false;
	}
	
	@Override
	public void focusGained(final FocusEvent arg0) {
		this.requestFocus();
	}
	
	@Override
	public void focusLost(final FocusEvent arg0) {
		// do nothing
	}
	
}
