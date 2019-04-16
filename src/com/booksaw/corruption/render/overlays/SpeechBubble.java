package com.booksaw.corruption.render.overlays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.sprites.Sprite;

public class SpeechBubble extends Overlay {

	String text;
	Point p;
	int width = -1, height = 100;
	private final int PADDING = 5, CORNERRAD = 10;
	Sprite focus;

	public SpeechBubble(Sprite focus, String text) {
		this.text = text;
		this.focus = focus;

		int lines = 1;

		height = Config.f.getSize() + (2 * PADDING * lines) + 5;
		p = new Point(0, 0);
	}

	public SpeechBubble(Point p, String text) {
		this((Sprite) null, text);

		this.p = p;

	}

	@Override
	public void render(Graphics g) {
		if (width == -1) {
			setWidth(g);
		}

		if (focus != null) {
			p.x = (focus.getX() + (focus.getWidth() / 2) - (width / 2)) - GameCamera.activeCamera.x;
			p.y = focus.getY() + (focus.getHeight()) + 10 + height;
		}

		Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.WHITE);
		g.fillRoundRect(p.x, GameCamera.cameraHeight - p.y, width, height, CORNERRAD, CORNERRAD);

		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(p.x, GameCamera.cameraHeight - p.y, width, height, CORNERRAD, CORNERRAD);

		g2d.setFont(Config.f);
		g2d.drawString(text, p.x + PADDING, GameCamera.cameraHeight - p.y + PADDING + Config.f.getSize());

	}

	private void setWidth(Graphics g) {
		g.setFont(Config.f);
		width = g.getFontMetrics().stringWidth(text) + (PADDING * 2);
	}

	@Override
	public void resize() {

	}

}
