package com.booksaw.corruption.level.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.level.Dimensions;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.Location;
import com.booksaw.corruption.selection.Selectable;

/**
 * Abstract class for game objects
 * 
 * @author James
 *
 */
public abstract class GameObject extends Selectable implements Location, Dimensions {

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
	protected int width = 0, height = 0;

	// Returns the rectangle of the object
	public Rectangle getRectangle() {
//		Rectangle r = new Rectangle(x, y, width, height);
//		System.out.println("obj" + r.x + " " + r.y + " " + r.width + " " + r.height);
		return new Rectangle((int) x, (int) y, width, height);
	}

	/**
	 * Used to render the object
	 * 
	 * @param g      the graphics to render
	 * @param camera the information of the camera
	 */
	public void render(Graphics g, Rectangle camera) {
		renderS(g, camera);

		if (selected) {
			int cameraX = camera.x;
			int cameraHeight = camera.height;
			int cameraY = camera.y;

			g.setColor(Color.WHITE);
			g.drawRect((int) (x - cameraX), (int) (cameraHeight - (y + cameraY + (getHeight()))), (int) (getWidth()),
					(int) (getHeight()));
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval((int) x - circleD / 2 - cameraX, (int) (cameraHeight - ((int) y + circleD / 2)), circleD,
					circleD);
			g.fillOval((int) ((int) x + (getWidth())) - circleD / 2 - cameraX,
					(int) (cameraHeight - ((int) y + circleD / 2)), circleD, circleD);
			g.fillOval((int) (int) x - circleD / 2 - cameraX,
					(int) (cameraHeight - ((int) y + circleD / 2 + (getHeight()))), circleD, circleD);
			g.fillOval((int) ((int) x + (getWidth())) - circleD / 2 - cameraX,
					(int) (cameraHeight - ((int) y + circleD / 2 + (getHeight()))), circleD, circleD);

		}

	}

	public abstract void renderS(Graphics g, Rectangle camera);

	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width > 0)
			this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height > 0)
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

	@Override
	public void applyOffset(Point p) {
		x += p.x;
		y += p.y;
	}

	@Override
	public void delete() {
		LevelManager.activeLevel.removeObject(this);
	}

}
