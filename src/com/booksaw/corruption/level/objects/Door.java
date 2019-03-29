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

	int centrex, centrey;

	static {
		doorClosed = Utils.getImage(new File(PATH + "doorclosed.png"));
		doorOpen = Utils.getImage(new File(PATH + "dooropen.png"));
	}

	boolean open;
	boolean left;

	public Door(String info) {
		String[] split = info.split(";");
		centrex = Integer.parseInt(split[0]);
		centrey = Integer.parseInt(split[1]);
		setOpen(Boolean.parseBoolean(split[2]));
		left = Boolean.parseBoolean(split[3]);
		width = Integer.parseInt(split[4]);

		height = doorClosed.getHeight() * Sprite.PIXELMULT;

		height = Integer.parseInt(split[5]);

		calculatePosition();
	}

	public Door(Point p, boolean open) {
		this.open = open;
		centrex = p.x;
		centrey = p.y;

		left = true;

		height = doorClosed.getHeight() * Sprite.PIXELMULT;
		width = ((open) ? doorOpen.getWidth() : doorClosed.getWidth()) * Sprite.PIXELMULT;
		collisionMode = (open) ? Mode.SOLID : Mode.IGNORE;
		calculatePosition();
	}

	@Override
	public void renderS(Graphics g, Rectangle camera) {

		if (open) {
//			g.drawImage(doorOpen, (int) x - camera.x, (camera.height + camera.y) - ((int) y + height), width, height,
//					null);
			g.drawImage(doorOpen, (int) x - camera.x, (camera.height + camera.y) - ((int) y + height),
					(int) (x - camera.x) + width, (camera.height + camera.y) - ((int) y),
					(!left) ? 0 : doorOpen.getWidth(), 0, (left) ? 0 : doorOpen.getWidth(), doorOpen.getHeight(), null);

		} else {
//			g.drawImage(doorClosed, (int) x - camera.x, (camera.height + camera.y) - ((int) y + height), width, height,
//					null);

			g.drawImage(doorClosed, (int) x - camera.x, (camera.height + camera.y) - ((int) y + height),
					(int) (x - camera.x) + width, (camera.height + camera.y) - ((int) y),
					(left) ? 0 : doorClosed.getWidth(), 0, (!left) ? 0 : doorClosed.getWidth(), doorClosed.getHeight(),
					null);

		}
	}

	public void open() {
		open = true;
		collisionMode = Mode.IGNORE;
		width = doorOpen.getWidth() * Sprite.PIXELMULT;
		calculatePosition();
	}

	public void close() {
		width = doorClosed.getWidth() * Sprite.PIXELMULT;
		open = false;
		collisionMode = Mode.SOLID;
		calculatePosition();
	}

	@Override
	public String toString() {
		calculatePosition();
		return "object:door:" + (int) centrex + ";" + (int) centrey + ";" + open + ";" + left + ";" + width + ";"
				+ height;
	}

	public void setLocation(Point p) {
		centrex = p.x;
		centrey = p.y;

		calculatePosition();
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

	private void calculatePosition() {
		if (left) {
			x = centrex - width;
			y = centrey;

		} else {
			x = centrex;
			y = centrey;
		}
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
		calculatePosition();
	}

	@Override
	public void applyOffset(Point p) {
		centrex += p.x;
		centrey += p.y;

		calculatePosition();

	}

}
