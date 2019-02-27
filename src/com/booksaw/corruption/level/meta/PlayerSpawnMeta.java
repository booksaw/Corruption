package com.booksaw.corruption.level.meta;

import java.awt.Point;

import com.booksaw.corruption.renderControler.GameController;

/**
 * Used to set the location of the player spawn
 * 
 * @author James
 *
 */
public class PlayerSpawnMeta extends Meta {

	Point spawn;

	public PlayerSpawnMeta(String info) {
		super(info);

		String[] split = info.split(";");
		spawn = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

	}

	@Override
	public void execute() {
		GameController.gameController.getActivePlayer().setX(spawn.x);
		GameController.gameController.getActivePlayer().setY(spawn.y);
	}

}
