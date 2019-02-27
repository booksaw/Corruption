package com.booksaw.corruption.render.overlays;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.GameCamera;

public class EditorOverlay extends Overlay {

	public static BufferedImage saveIcon, greenIcon, add;
	public static final int SQUARE = 40;

	static {
		saveIcon = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "save.png"));
		greenIcon = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "savegreen.png"));
		add = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "add.png"));
	}

	@Override
	public void render(Graphics g) {

		if (LevelManager.activeLevel.hasChanged()) {
			g.drawImage(saveIcon, GameCamera.cameraWidth - SQUARE, GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE,
					null);
		} else {
			g.drawImage(greenIcon, GameCamera.cameraWidth - SQUARE, GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE,
					null);

		}

		g.drawImage(add, GameCamera.cameraWidth - (SQUARE * 2), GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE, null);

	}

}
