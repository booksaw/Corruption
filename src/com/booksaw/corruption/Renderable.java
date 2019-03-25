package com.booksaw.corruption;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Used to set the priority of rendered objects in the game
 * 
 * @author James
 *
 */
public abstract class Renderable {

	/**
	 * Keep between 0 and 100
	 */
	protected int priority = 15;

	/**
	 * Used to give the rending priority of the object, lower the better
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Used to paint the object
	 * @param g the graphics controller
	 * @param r the camera rectangle
	 */
	public abstract void paint(Graphics g, Rectangle r);

}
