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

public class Spike extends GameObject {

	Direction d;
	private static BufferedImage spikes, spikesR;
	private final static String path = Config.ASSETSPATH + File.separator + "spikes" + File.separator;
	int count = 1;

	static {
		spikes = Utils.getImage(new File(path + "spike.png"));
		spikesR = rotateClockwise90(spikes);
	}

	public static BufferedImage rotateClockwise90(BufferedImage src) {

		int width = src.getWidth();
		int height = src.getHeight();

		BufferedImage dest = new BufferedImage(height, width, src.getType());

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				try {
					dest.setRGB(height - y - 1, x, spikes.getRGB(x, y));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		return dest;
	}

	public Spike(String info) {

		collisionMode = Mode.DEATH;
		// getting object data from the split
		String[] split = info.split(";");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		width = Integer.parseInt(split[2]);
		height = Integer.parseInt(split[3]);

		switch (split[4]) {
		case "l":
			d = Direction.LEFT;
			break;
		case "r":
			d = Direction.RIGHT;
			break;
		case "u":
			d = Direction.UP;
			break;
		case "d":
			d = Direction.DOWN;
			break;
		}
		try {
			uuid = UUID.fromString(split[5]);
		} catch (Exception e) {
			uuid = generateUUID();
		}
		generateCount();

	}

	public Spike(Point p, Direction d) {
		collisionMode = Mode.DEATH;
		x = p.x;
		y = p.y;
		width = spikes.getWidth() * Sprite.PIXELMULT;
		height = spikes.getHeight() * Sprite.PIXELMULT;
		this.d = d;
		uuid = generateUUID();
		generateCount();
	}

	@Override
	public void renderS(Graphics g, Rectangle camera) {
		if (d == Direction.UP) {
			int tWidth = width / count;
			int x = (int) this.x - camera.x;
			for (int i = 0; i < count; i++) {
				g.drawImage(spikes, x, camera.height - (int) y, x + tWidth, camera.height - ((int) y + height), 0,
						spikes.getHeight(), spikes.getWidth(), 0, null);
				x += tWidth;
			}
		} else if (d == Direction.DOWN) {
			int tWidth = width / count;
			int x = (int) this.x - camera.x;
			for (int i = 0; i < count; i++) {
				g.drawImage(spikes, x, camera.height - (int) y, x + tWidth, camera.height - ((int) y + height), 0, 0,
						spikes.getWidth(), spikes.getHeight(), null);
				x += tWidth;
			}
		} else if (d == Direction.RIGHT) {
			int tHeight = height / count;
			int y = camera.height - (int) this.y;
			int x = (int) this.x - camera.x;
			for (int i = 0; i < count; i++) {

				g.drawImage(spikesR, x, y, x + width, y - tHeight, 0, spikesR.getHeight(), spikesR.getWidth(), 0, null);

				y -= tHeight;
			}

		} else if (d == Direction.LEFT) {
			int tHeight = height / count;
			int y = camera.height - (int) this.y;
			int x = (int) this.x - camera.x;
			for (int i = 0; i < count; i++) {

				g.drawImage(spikesR, x, y, x + width, y - tHeight, spikesR.getWidth(), spikesR.getHeight(), 0, 0, null);

				y -= tHeight;
			}
		}
	}

	public enum Direction {
		LEFT("Left", "l"), RIGHT("Right", "r"), UP("Up", "u"), DOWN("Down", "d");

		String out, file;

		private Direction(String out, String file) {
			this.out = out;
			this.file = file;
		}

		@Override
		public String toString() {
			return out;
		}

		public String getFileOutput() {
			return file;
		}
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);

		generateCount();
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		generateCount();
	}

	private void generateCount() {

		if (d == Direction.LEFT || d == Direction.RIGHT) {
			int tempH = spikes.getHeight() * Sprite.PIXELMULT;

			if (height % tempH < tempH / 2) {
				count = (int) Math.floor(height / tempH);
			} else {
				count = (int) Math.ceil(height / tempH);
			}

		} else {
			int tempW = spikes.getWidth() * Sprite.PIXELMULT;

			if (width % tempW < tempW / 2) {
				count = (int) Math.floor(width / tempW);
			} else {
				count = (int) Math.ceil(width / tempW);
			}

		}

		if (count <= 0) {
			count = 1;
		}

	}

	public void setDirection(Direction d) {
		this.d = d;
		generateCount();
	}

	public Direction getDirection() {
		return d;
	}

	@Override
	public String toString() {
		return "object:spike:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + d.getFileOutput() + ";" + uuid;

	}

}
