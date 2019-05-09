package com.booksaw.corruption.level.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.audioEngine.AudioInstance;
import com.booksaw.corruption.audioEngine.AudioPlayer;
import com.booksaw.corruption.sprites.Sprite;

public class Door extends GameObject {

	private static BufferedImage doorClosed, doorOpen;
	static final String PATH = Config.ASSETSPATH + File.separator + "door" + File.separator;

	int centrex, centrey;

	static {
		doorClosed = Utils.getImage(new File(PATH + "doorclosed.png"));
		doorOpen = Utils.getImage(new File(PATH + "dooropen.png"));
	}

	boolean startingOpen, open;
	boolean left;

	public Door(String info) {
		super();
		String[] split = info.split(";");
		centrex = Integer.parseInt(split[0]);
		centrey = Integer.parseInt(split[1]);

		left = Boolean.parseBoolean(split[3]);
		width = Integer.parseInt(split[4]);
		setOpen(Boolean.parseBoolean(split[2]));
		height = doorClosed.getHeight() * Sprite.PIXELMULT;

		height = Integer.parseInt(split[5]);
		try {
			uuid = UUID.fromString(split[6]);
		} catch (Exception e) {
			uuid = generateUUID();
		}
		calculatePosition();
	}

	public Door(Point p, boolean open) {
		super();
		centrex = p.x;
		centrey = p.y;

		left = true;

		height = doorClosed.getHeight() * Sprite.PIXELMULT;
		width = ((open) ? doorOpen.getWidth() : doorClosed.getWidth()) * Sprite.PIXELMULT;
		collisionMode = (open) ? Mode.SOLID : Mode.IGNORE;
		setOpen(open);
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
				+ height + ";" + uuid;
	}

	public void setLocation(Point p) {
		centrex = p.x;
		centrey = p.y;

		calculatePosition();
	}

	public void setOpen(boolean open) {
		startingOpen = open;
		if (open) {
			open();
		} else {
			close();
		}
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isStartingOpen() {
		return startingOpen;
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

	@Override
	public String getCopy() {
		calculatePosition();
		return "object:door:" + (int) centrex + ";" + (int) centrey + ";" + startingOpen + ";" + left + ";" + width
				+ ";" + height + ";" + generateUUID();
	}

	@Override
	public void trigger(String[] args) {
		super.trigger(args);
		switch (args[0]) {
		case "open":
			boolean change = Boolean.parseBoolean(args[1]);
			// checking if there is a state change
			if (change != open)
				AudioPlayer.playSound(AudioInstance.DOOR.getClip());
			if (Boolean.parseBoolean(args[1])) {
				open();
			} else
				close();
		}

	}

}
