package com.booksaw.corruption.level.background;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import com.booksaw.corruption.level.Dimensions;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.Location;
import com.booksaw.corruption.selection.Selectable;

public abstract class Background extends Selectable implements Dimensions, Location {

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

	public Background() {
		priority = 50;
	}

	int width, height;

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
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public void applyOffset(Point p) {
		x += p.x;
		y += p.y;
	}

	@Override
	public void delete() {
		LevelManager.activeLevel.removeBackground(this);
	}

	public boolean needsSaving() {

		if (width < 5 || height < 5)
			return false;
		return true;
	}
}
