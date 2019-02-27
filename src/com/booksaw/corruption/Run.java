package com.booksaw.corruption;

import javax.swing.UIManager;

/**
 * Literally creates a new instance of Corruption in standard use. Separated
 * from Corruption so quick backdoors can be created
 * 
 * @author James
 *
 */
public class Run {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		new Corruption();
	}

}
