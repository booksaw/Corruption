package com.booksaw.corruption.level.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.sprites.Sprite;

public class Slime extends GameObject {

	private static BufferedImage slimeTop, slimeMain;

	static {
		String location = Config.ASSETSPATH + File.separator + "slime" + File.separator;
		slimeTop = Utils.getImage(new File(location + "slimetop.png"));
		slimeMain = Utils.getImage(new File(location + "slimemain.png"));
	}

	int mainHeight, topHeight, repeats, yIterations;

	public Slime(String info) {
		super();
		collisionMode = Mode.DEATH;
		// getting object data from the split
		String[] split = info.split(";");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		width = Integer.parseInt(split[2]);
		height = Integer.parseInt(split[3]);
		try {
			uuid = UUID.fromString(split[4]);
		} catch (Exception e) {
			uuid = generateUUID();
		}
		calculateNumbers();

	}

	public Slime(Point p) {
		super();
		collisionMode = Mode.DEATH;
		x = p.x;
		y = p.y;
		width = slimeTop.getWidth() * Sprite.PIXELMULT;
		height = slimeTop.getHeight() * Sprite.PIXELMULT;
		uuid = generateUUID();
		calculateNumbers();
	}

	@Override
	public void renderS(Graphics g, Rectangle camera) {
		int tWidth = width / repeats;
		int tHeight = mainHeight / yIterations;
		int x = (int) this.x - camera.x;

		for (int i = 0; i < repeats; i++) {
			g.drawImage(slimeTop, x, camera.height - (int) y - mainHeight - topHeight, tWidth, topHeight, null);

			int y = camera.height - (int) this.y - mainHeight;
			for (int j = 0; j < yIterations; j++) {
				g.drawImage(slimeMain, x, y, tWidth, tHeight, null);
				y += tHeight;
			}

			x += tWidth;
		}

	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		calculateNumbers();
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		calculateNumbers();

	}

	@Override
	public String toString() {
		return "object:slime:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + uuid;
	}

	public void calculateNumbers() {

		if (height < slimeTop.getHeight() * Sprite.PIXELMULT) {
			topHeight = height;
			mainHeight = 0;
			return;
		}

		topHeight = slimeTop.getHeight() * Sprite.PIXELMULT;
		mainHeight = height - topHeight;

		int tempW = slimeMain.getWidth() * Sprite.PIXELMULT;

		if (width % tempW < tempW / 2) {
			repeats = (int) Math.floor(width / tempW);
		} else {
			repeats = (int) Math.ceil(width / tempW);
		}

		if (repeats <= 0) {
			repeats = 1;
		}

		int tempH = slimeMain.getHeight() * Sprite.PIXELMULT;

		if (mainHeight % tempH < tempW / 2) {
			yIterations = (int) Math.floor(mainHeight / tempH);
		} else {
			yIterations = (int) Math.ceil(mainHeight / tempH);
		}

		if (yIterations <= 0) {
			yIterations = 1;
		}

	}

	@Override
	public String getCopy() {
		return "object:slime:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + generateUUID();
	}

}
