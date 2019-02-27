package com.booksaw.corruption.level.meta;

import java.awt.Color;

import com.booksaw.corruption.level.LevelManager;

/**
 * Used to set the background colour of the level
 * 
 * @author James
 *
 */
public class BackgroundColorMeta extends Meta {

	Color c;

	public BackgroundColorMeta(String info) {
		super(info);

		String[] split = info.split(";");
		c = new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));

	}

	@Override
	public void execute() {
		LevelManager.activeLevel.backgroundColor = c;
	}

	@Override
	public String toString() {
		return "meta:backgroundcolor:" + c.getRed() + ";" + c.getGreen() + ";" + c.getBlue();
	}

}
