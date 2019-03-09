package com.booksaw.corruption;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * Stores static final info
 * 
 * @author James
 *
 */
public class Config {

	public static final String ASSETSPATH = "resources";

	static void load() {
		logo = new ImageIcon(Utils.getImage(new File(ASSETSPATH + File.separator + "logoicon.png")));
	}

	public static ImageIcon logo = null;

}
