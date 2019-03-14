package com.booksaw.corruption.render.overlays;

import java.awt.Graphics;
import java.io.File;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.GameCamera;

public class GameOverlay extends Overlay {

	String level = "";

	public GameOverlay() {
		File temp = LevelManager.activeLevel.getF();

		level = temp.getName().replaceFirst("[.][^.]+$", "");
	}

	private final int INDENT = 10;

	@Override
	public void render(Graphics g) {

		g.setColor(Config.fontColor);
		g.setFont(Config.f);

		g.drawString("Level: " + level, INDENT, INDENT + Config.f.getSize());
		g.drawString("Time: " + LevelManager.activeLevel.time,
				GameCamera.cameraWidth
						- (INDENT + g.getFontMetrics().stringWidth("Time: " + LevelManager.activeLevel.time)),
				INDENT + Config.f.getSize());
		g.drawString("Fails: " + LevelManager.activeLevel.fails,
				GameCamera.cameraWidth / 2
						- g.getFontMetrics().stringWidth("Fails: " + LevelManager.activeLevel.fails) / 2,
				INDENT + Config.f.getSize());
	}

	@Override
	public void resize() {

	}

}
