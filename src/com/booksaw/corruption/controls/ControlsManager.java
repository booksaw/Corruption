package com.booksaw.corruption.controls;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.configuration.YamlConfiguration;

public class ControlsManager {

	private static HashMap<String, Control> controls = new HashMap<>();
	private static YamlConfiguration yaml;

	public static void loadControls() {

		File f = new File("controls.yaml");

		if (!f.exists()) {

			try {
				f.createNewFile();
			} catch (IOException e1) {
			}

			try (OutputStream out = new FileOutputStream(f)) {
				Files.copy(Paths.get(Config.ASSETSPATH + File.separator + "controls.yaml"), out);
			} catch (Exception e) {
				System.err.println("could not copy controls file");
			}
		}

		yaml = new YamlConfiguration(f);

		// looping through each option
		List<String> search = yaml.getOptions("");

		for (String str : search) {
			List<String> temp = yaml.getOptions(str);
			for (String s : temp) {
				controls.put(str + "." + s, new Control(str + "." + s, yaml.getString(str + "." + s)));
			}

		}

	}

	public static Control getKeyOptions(String reference) {
		return controls.get(reference);
	}

	public static boolean isKeyUsed(String reference, KeyEvent e) {

		Control c = getKeyOptions(reference);

		for (int i : c.keys) {
			if (e.getKeyCode() == i) {
				return true;
			}
		}

		return false;

	}

	public static boolean isKeyUsed(ControlList reference, KeyEvent e) {
		return isKeyUsed(reference.toString(), e);
	}

}
