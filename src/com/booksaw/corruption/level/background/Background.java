package com.booksaw.corruption.level.background;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public abstract class Background {

	public static Background getBackground(Point p, List<Background> backgrounds) {
		return getBackground(new Rectangle(p, new Dimension(1, 1)), backgrounds);
	}

	public static Background getBackground(Rectangle r, List<Background> backgrounds) {

		for (Background temp : backgrounds) {
			if (temp.getRectangle().intersects(r)) {
				return temp;
			}
		}

		return null;
	}

	int x, y, width, height;

	public abstract void draw(Graphics g, Rectangle camera);

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

	protected Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}


}
