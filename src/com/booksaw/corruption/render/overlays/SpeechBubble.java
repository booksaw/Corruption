package com.booksaw.corruption.render.overlays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Updatable;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.sprites.Sprite;

public class SpeechBubble extends Overlay implements Updatable {

	String[] text;
	Point p;
	int width = -1, height = 100;
	private final int PADDING = 5, CORNERRAD = 10, MAXTIME = 100;
	Sprite focus;
	int count = 0, time = 0, length;

	public SpeechBubble(Sprite focus, String text) {
		this.text = new String[] { text };
		length = text.length();
		this.focus = focus;

		height = Config.f.getSize() + (2 * PADDING) + 5;
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
			if (p.y + height > GameCamera.cameraHeight) {
				p.y = focus.getY() - (10);
			}
		}

		Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.WHITE);
		g.fillRoundRect(p.x, GameCamera.cameraHeight - p.y, width, height, CORNERRAD, CORNERRAD);

		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(p.x, GameCamera.cameraHeight - p.y, width, height, CORNERRAD, CORNERRAD);

		g2d.setFont(Config.f);
		int y = GameCamera.cameraHeight - p.y + PADDING + Config.f.getSize();
		int displayedCount = 0;
		for (String str : text) {
			// if all displayed characters have already been displayed
			if (displayedCount >= count) {
				break;
			}
			if (count - displayedCount < str.length()) {
				g2d.drawString(str.substring(0, count - displayedCount),
						p.x + (PADDING / 2) + (width / 2 - g.getFontMetrics().stringWidth(str) / 2), y);
			} else {
				g2d.drawString(str, p.x + (PADDING / 2) + (width / 2 - g.getFontMetrics().stringWidth(str) / 2), y);

			}

			y += Config.f.getSize() + 5;
			displayedCount += str.length();
		}
	}

	private void setWidth(Graphics g) {
		g.setFont(Config.f);
		width = g.getFontMetrics().stringWidth(text[0]) + (PADDING * 2);
		int lines = 1;
		if (width > 100) {
			lines++;
			boolean found = false;
			// finding the centre space
			System.out.println((text[0].charAt(text[0].length() / 2)) + " = character"
					+ ((text[0].charAt(text[0].length() / 2)) == ' '));

			if (text[0].length() % 2 == 0 && text[0].charAt(text[0].length() / 2) == ' ') {
				found = true;
				String str = text[0];
				text = new String[2];
				text[0] = str.substring(0, str.length() / 2);
				text[1] = str.substring(str.length() / 2 + 1, str.length());

			}
			if (!found) {
				for (int i = 0; i < text[0].length() / 2; i++) {
					try {
						if (text[0].charAt(text[0].length() / 2 + i) == ' ') {
							found = true;
							String str = text[0];
							text = new String[2];
							text[0] = str.substring(0, str.length() / 2 + i);
							text[1] = str.substring(str.length() / 2 + 1 + i, str.length());
							break;
						} else if (text[0].charAt(text[0].length() / 2 - i) == ' ') {
							found = true;
							String str = text[0];
							text = new String[2];
							text[0] = str.substring(0, str.length() / 2 - i);
							text[1] = str.substring(str.length() / 2 + 1 - i, str.length());
							break;
						}
					} catch (Exception e) {
						break;
					}
				}
			}

			int widest = 0;
			for (String str : text) {
				int temp = g.getFontMetrics().stringWidth(str);
				if (temp > widest) {
					widest = temp;
				}
			}
			if (found) {
				width = widest + (PADDING * 2);
				height = Config.f.getSize() + (2 * PADDING * lines) + 5 + (5 * lines);
			}
		}
	}

	@Override
	public void resize() {

	}

	@Override
	public void show() {
		LevelManager.activeLevel.getUpdatable().add(this);
	}

	@Override
	public void hide() {
		LevelManager.activeLevel.getUpdatable().remove(this);
	}

	@Override
	public void update(int time) {
		this.time += time;

		if (this.time < MAXTIME || count >= length) {
			return;
		}
		// if another character needs to be added
		count++;
		this.time = 0;

	}

}
