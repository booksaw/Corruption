package com.booksaw.corruption.level.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.UUID;

import com.booksaw.corruption.render.GameCamera;

/**
 * Basic object which is just solid colour
 * 
 * @author James
 *
 */
public class Block extends GameObject {
	// the objects colour
	Color c;

	/*
	 * Constructor for the object using a reference
	 */
	public Block(String ref) {
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

	public Block(Rectangle position, Color c) {
		x = position.x;
		y = position.y;
		width = position.width;
		height = position.height;
		uuid = generateUUID();
		this.c = c;
	}

	/**
	 * Used to render the object
	 */
	@Override
	public void renderS(Graphics g, Rectangle camera) {
		g.setColor(c);
		Rectangle r = getRectangle();
		if (r.intersects(camera)) {
			g.fillRect((r.x - camera.x), GameCamera.cameraHeight - r.y - r.height - camera.y, r.width, r.height);
		}
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return "object:block:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + c.getRed() + ";"
				+ c.getGreen() + ";" + c.getBlue() + ";" + uuid;
	}

	@Override
	public String getCopy() {
		return "object:block:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + c.getRed() + ";"
				+ c.getGreen() + ";" + c.getBlue() + ";" + generateUUID();
	}

}
