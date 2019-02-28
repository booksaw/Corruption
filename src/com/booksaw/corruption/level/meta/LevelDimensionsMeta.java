package com.booksaw.corruption.level.meta;

import java.awt.Dimension;

import com.booksaw.corruption.level.LevelManager;

/**
 * Used to set the size of the level
 * 
 * @author James
 *
 */
public class LevelDimensionsMeta extends Meta {

	public String[] split;

	public LevelDimensionsMeta(String info) {
		super(info);
		split = info.split(";");

	}

	@Override
	public void execute() {
		LevelManager.activeLevel.levelDimensions = new Dimension(Integer.parseInt(split[0]),
				Integer.parseInt(split[1]));
	}

	@Override
	public String toString() {
		return "meta:leveldimensions:" + split[0] + ";" + split[1];
	}
}
