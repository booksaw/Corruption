package com.booksaw.corruption.level.meta;

import com.booksaw.corruption.render.GameCamera;

/**
 * Used to set the starting camera location of the level
 * 
 * @author James
 *
 */
public class CameraLocationMeta extends Meta {

	public String[] split;

	public CameraLocationMeta(String info) {
		super(info);
		split = info.split(";");

	}

	@Override
	public void execute() {
		GameCamera.activeCamera.x = Integer.parseInt(split[0]);
		GameCamera.activeCamera.y = Integer.parseInt(split[1]);
	}

	@Override
	public String toString() {
		return "meta:cameralocation:" + split[0] + ";" + split[1];
	}

}
