package com.booksaw.corruption.level.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.sprites.Sprite;

public class Door extends GameObject {

	private static BufferedImage doorClosed, doorOpen;
	static final String PATH = Config.ASSETSPATH + File.separator + "door" + File.separator;

	static {
		doorClosed = Utils.getImage(new File(PATH + "doorclosed.png"));
		doorOpen = Utils.getImage(new File(PATH + "dooropen.png"));
	}

	boolean open;

	public Door(String info) {
		String[] split = info.split(";");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		open = Boolean.parseBoolean(split[2]);

		height = doorClosed.getHeight() * Sprite.PIXELMULT;
		width = ((open) ? doorOpen.getWidth() : doorClosed.getWidth()) * Sprite.PIXELMULT;
		collidable = (open) ? false : true;
	}

	public Door(Point p, boolean open) {
		this.open = open;
		x = p.x;
		y = p.y;

		height = doorClosed.getHeight() * Sprite.PIXELMULT;
		width = ((open) ? doorOpen.getWidth() : doorClosed.getWidth()) * Sprite.PIXELMULT;
		collidable = (open) ? false : true;
	}

	@Override
	public void renderS(Graphics g, Rectangle camera) {
		if (open) {
			g.drawImage(doorOpen, x - camera.x, (camera.height + camera.y) - (y + height), width, height, null);
		} else {
			g.drawImage(doorClosed, x - camera.x, (camera.height + camera.y) - (y + height), width, height, null);

		}
	}

	public void open() {
		open = true;
		collidable = false;
		width = doorOpen.getWidth() * Sprite.PIXELMULT;
	}

	public void close() {
		width = doorClosed.getWidth() * Sprite.PIXELMULT;
		open = false;
		collidable = true;
	}

	@Override
	public String toString() {

		return "object:door:" + x + ";" + y + ";" + open;
	}

	public void setLocation(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public void setOpen(boolean open) {
		if (open) {
			open();
		} else {
			close();
		}
	}

	public boolean isOpen() {
		return open;
	}

}
