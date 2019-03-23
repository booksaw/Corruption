package com.booksaw.corruption.level.interactable;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.booksaw.corruption.Renderable;

public class InteractableComponent extends Renderable {

	Interactable parent;
	BufferedImage img;

	public InteractableComponent(Interactable parent, BufferedImage img, int priority) {
		this.parent = parent;
		this.img = img;
		this.priority = priority;
	}

	@Override
	public void paint(Graphics g, Rectangle c) {
		g.drawImage(img, (int) parent.getX() - c.x, c.height - (int) parent.getY() - parent.height, parent.width,
				parent.height, null);
	}

	public BufferedImage getImg() {
		return img;
	}
	
	

}
