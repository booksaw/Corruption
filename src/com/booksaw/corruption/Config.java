package com.booksaw.corruption;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	/**
	 * Used to remove the first element in an array
	 * @param toRemove
	 * @return
	 */
	public static String[] removeFirstElement(String[] toRemove) {
		
		List<String> converted = new ArrayList<>(Arrays.asList(toRemove));
		converted.remove(0);
		return converted.toArray(new String[converted.size()]);
		
	}

}
