package com.booksaw.corruption.listeners;

import javax.swing.JFrame;

/**
 * Used for all listerns to prep them
 * @author James
 *
 */
public interface Listener {

	/**
	 * Used to activate key listeners
	 * @param f
	 */
	public void activate(JFrame f);
	
	/**
	 * Used to disable key listenrs
	 * @param f
	 */
	public void disable(JFrame f);
}
