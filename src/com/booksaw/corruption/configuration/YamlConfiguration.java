package com.booksaw.corruption.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;

public class YamlConfiguration {

	private Map<String, Object> obj;
	private File f;
	private Yaml yaml;

	public YamlConfiguration(File f) {
		this.f = f;
		yaml = new Yaml();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		obj = yaml.load(inputStream);
	}

	@SuppressWarnings("unchecked")
	public Object getSetting(String ref) {
		String[] split = ref.split("\\.");
		Object toReturn = null;

		for (String temp : split) {
			if (toReturn == null) {
				toReturn = obj.get(temp);
				continue;
			}

			if (toReturn instanceof HashMap<?, ?>)

				toReturn = ((HashMap<String, Object>) toReturn).get(temp);

		}

		return toReturn;
	}

	public void set(String ref, Object value) {
		obj.put(ref, value);

	}

	public void saveConfiguration() {

//		try {
//			yaml.dump(saveCon(obj, 0), new FileWriter(f));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		FileWriter pw;
		try {
			pw = new FileWriter(f);
			pw.write(saveCon(obj, 0, ""));
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	/**
	 * Uses recursion to generate a final string to be printed which represents the file
	 * 
	 * @param obj - The map to generate the output for
	 * @param level - The indentation level (usually leave at 0 and leave the recursion to change
	 * @param priortree - The prior reference ie (foo.bar)
	 * @return - The output for that map 
	 */
	@SuppressWarnings("unchecked")
	private String saveCon(Map<String, Object> obj, int level, String priortree) {
		if (obj == null) {
			return "";
		}

		String indent = "";
		for (int i = 0; i < level; i++) {
			indent = indent + "  ";
		}
		String toReturn = "";

		for (Entry<String, Object> temp : obj.entrySet()) {
			String ref = (priortree.equals("")) ? temp.getKey() : priortree + "." + temp.getKey();
			Object out = getSetting(ref);

			if (out instanceof ArrayList<?>) {
				toReturn = toReturn + indent + temp.getKey() + ":\n";

				for (String r : ((ArrayList<String>) out)) {
					toReturn = toReturn + indent + "- " + r + "\n";
				}

				continue;
			}
			if (!(out instanceof Map<?, ?>)) {
				String[] split = temp.getKey().split("\\.");
				toReturn = toReturn + indent + split[split.length - 1] + ": " + out + "\n";
				continue;
			}

			toReturn = toReturn + indent + temp.getKey() + ":\n";

			toReturn = toReturn + saveCon(((Map<String, Object>) out), level + 1, ref);

		}
		return toReturn;
	}

}
