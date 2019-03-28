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

	/**
	 * 	The path to the resources folder
	 */
	public static final String ASSETSPATH = "resources";
	/**
	 *  The standard font to use
	 */
	public static final Font f = new Font("Dialogue", Font.PLAIN, 20);
	/**
	 * The standard font color to use
	 */
	public static Color fontColor = Color.GREEN;

	/**
	 * Used to load a logo image
	 */
	public static void load() {
		logo = new ImageIcon(Utils.getImage(new File(ASSETSPATH + File.separator + "logoicon.png")));
	}

	/**
	 * Stores the logo image for use
	 */
	public static ImageIcon logo = null;

}
