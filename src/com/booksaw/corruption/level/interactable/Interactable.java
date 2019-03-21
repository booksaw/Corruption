package com.booksaw.corruption.level.interactable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.selection.Selectable;
import com.booksaw.corruption.sprites.Sprite;

public class Interactable extends Selectable {

	public static Interactable getInteractable(Point p, List<Interactable> interactables) {
		return getInteractable(new Rectangle(p, new Dimension(1, 1)), interactables);
	}

	public static Interactable getInteractable(Rectangle r, List<Interactable> interactable) {

		for (Interactable temp : interactable) {
			if (temp.getRectangle().intersects(r)) {
				return temp;
			}
		}

		return null;
	}

	private final String PATH = Config.ASSETSPATH + File.separator + "interactable" + File.separator;

	String name;
	BufferedImage image;
	int width, height;

	public Interactable(String ref, boolean select) {
		super();
		priority = 25;
		// getting object data from the split
		String[] split = ref.split(";");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		width = Integer.parseInt(split[2]);
		height = Integer.parseInt(split[3]);

		name = split[4];

		image = Utils.getImage(new File(PATH + name + ".png"));

	}

	public Interactable(String name) {
		this.name = name;
		x = -1;
		y = -1;
		image = Utils.getImage(new File(PATH + name + ".png"));

		width = image.getWidth() * Sprite.PIXELMULT;
		height = image.getHeight() * Sprite.PIXELMULT;

	}

	@Override
	public int getX() {
		return (int) x;
	}

	@Override
	public int getY() {
		return (int) y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void delete() {
		LevelManager.activeLevel.removeInteractable(this);
	}

	@Override
	public void applyOffset(Point p) {
		x += p.x;
		y += p.y;
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public String toString() {
		return "interactable:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + name;
	}

	@Override
	public void paintComp(Graphics g, Rectangle c) {
		g.drawImage(image, (int) x - c.x, c.height - (int) y - height, width, height, null);

//		if (selected) {
//			int cameraX = c.x;
//			int cameraHeight = c.height;
//			int cameraY = c.y;
//
//			g.setColor(Color.WHITE);
//			g.drawRect((int) (x - cameraX), (int) (cameraHeight - (y + cameraY + (getHeight()))), (int) (getWidth()),
//					(int) (getHeight()));
//			g.setColor(Color.LIGHT_GRAY);
//			g.fillOval((int) x - circleD / 2 - cameraX, (int) (cameraHeight - ((int) y + circleD / 2)), circleD,
//					circleD);
//			g.fillOval((int) ((int) x + (getWidth())) - circleD / 2 - cameraX,
//					(int) (cameraHeight - ((int) y + circleD / 2)), circleD, circleD);
//			g.fillOval((int) (int) x - circleD / 2 - cameraX,
//					(int) (cameraHeight - ((int) y + circleD / 2 + (getHeight()))), circleD, circleD);
//			g.fillOval((int) ((int) x + (getWidth())) - circleD / 2 - cameraX,
//					(int) (cameraHeight - ((int) y + circleD / 2 + (getHeight()))), circleD, circleD);
//
//		}
	}

	public void setLocation(Point p) {
		x = p.x;
		y = p.y;
	}

}
