package com.booksaw.corruption.render.overlays;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.GameCamera;

public class EditorOverlay extends Overlay {

	public static BufferedImage saveIcon, greenIcon, add, menu, cursor, trash, sprite, table;
	public static final int SQUARE = 40;

	static {
		saveIcon = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "save.png"));
		greenIcon = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "savegreen.png"));
		add = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "add.png"));
		menu = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "menu.png"));
		cursor = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "cursor.png"));
		trash = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "trash.png"));
		sprite = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "sprite.png"));
		table = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "table.png"));
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

		g.drawImage(sprite, GameCamera.cameraWidth - (SQUARE * 2), GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE,
				null);
		g.drawImage(menu, GameCamera.cameraWidth - (SQUARE * 3), GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE,
				null);
		g.drawImage(cursor, GameCamera.cameraWidth - (SQUARE * 4), GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE,
				null);
		g.drawImage(trash, GameCamera.cameraWidth - (SQUARE * 5), GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE,
				null);
		g.drawImage(add, GameCamera.cameraWidth - (SQUARE * 6), GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE, null);
		g.drawImage(table, GameCamera.cameraWidth - (SQUARE * 7), GameCamera.cameraHeight - SQUARE, SQUARE, SQUARE,
				null);

	}

	@Override
	public void resize() {
	}

}
