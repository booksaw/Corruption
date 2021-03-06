package com.booksaw.corruption.level.background;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.UUID;

import com.booksaw.corruption.render.GameCamera;

public class ColoredBackground extends Background {

	Color c;

	public ColoredBackground(String ref) {
		super();
		// getting object data from the split
		String[] split = ref.split(";");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		width = Integer.parseInt(split[2]);
		height = Integer.parseInt(split[3]);

		c = new Color(Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6]));
		try {
			uuid = UUID.fromString(split[7]);
		} catch (Exception e) {
			uuid = generateUUID();
		}
	}

	public ColoredBackground(Rectangle position, Color c) {
		super();
		x = position.x;
		y = position.y;
		width = position.width;
		height = position.height;
		uuid = generateUUID();
		this.c = c;
	}

	@Override
	public void paintComp(Graphics g, Rectangle camera) {
		g.setColor(c);
		g.fillRect(((int) x - camera.x), GameCamera.cameraHeight - (int) y - height - camera.y, width, height);

	}

	@Override
	public String toString() {

		return "background:colored:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + c.getRed() + ";"
				+ c.getGreen() + ";" + c.getBlue() + ";" + uuid;

	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	@Override
	public String getCopy() {
		return "background:colored:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + c.getRed() + ";"
				+ c.getGreen() + ";" + c.getBlue() + ";" + generateUUID();

	}

}
