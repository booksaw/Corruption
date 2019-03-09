package com.booksaw.corruption.level.objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.level.Dimensions;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.Location;

/**
 * Abstract class for game objects
 * 
 * @author James
 *
 */
public abstract class GameObject implements Location, Dimensions {

	public boolean collidable = true;

	public static GameObject getObject(Point p) {
		return getObject(new Rectangle(p, new Dimension(1, 1)));
	}

	public static GameObject getObject(Rectangle r) {

		for (GameObject temp : LevelManager.activeLevel.getLevelObjects()) {
			if (temp.getRectangle().intersects(r)) {
				return temp;
			}
		}

		return null;
	}

	/**
	 * Location of the objects
	 */
	protected int x = 0, y = 0, width = 0, height = 0;

	// Returns the rectangle of the object
	public Rectangle getRectangle() {
//		Rectangle r = new Rectangle(x, y, width, height);
//		System.out.println("obj" + r.x + " " + r.y + " " + r.width + " " + r.height);
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Used to render the object
	 * 
	 * @param g      the graphics to render
	 * @param camera the information of the camera
	 */
	public abstract void render(Graphics g, Rectangle camera);

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean needsSaving() {

		if (width == 0 || height == 0)
			return false;
		return true;
	}

	public void setLocation(Point p) {
		x = p.x;
		y = p.y; 
	}

}
