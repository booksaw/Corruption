package com.booksaw.corruption;

import java.awt.Color;
import java.awt.Font;
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
	public static final Font f = new Font("Dialogue", Font.PLAIN, 20);
	public static Color fontColor = Color.GREEN;

	static void load() {
		logo = new ImageIcon(Utils.getImage(new File(ASSETSPATH + File.separator + "logoicon.png")));
	}

	public static ImageIcon logo = null;

}
