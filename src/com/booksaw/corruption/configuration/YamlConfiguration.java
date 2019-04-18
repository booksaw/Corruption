package com.booksaw.corruption.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			return;
		}

		obj = yaml.load(inputStream);
	}

	@SuppressWarnings("unchecked")
	public Object getSetting(String ref) {
		String[] split = ref.split("\\.");
		Object toReturn = null;

		for (String temp : split) {
			if (toReturn == null) {
				try {
					toReturn = obj.get(temp);
				} catch (Exception e) {
					return null;
				}
				continue;
			}

			if (toReturn instanceof HashMap<?, ?>)

				toReturn = ((HashMap<String, Object>) toReturn).get(temp);

		}

		return toReturn;
	}

	public void set(String ref, Object value) {
		if (ref.contains(".")) {
			dotSet(ref, value);
		} else {
			obj.put(ref, value);
		}

	}

	private HashMap<String, Object> getHash(String path, String[] args, int loc, Object toSet) {

		HashMap<String, Object> map = new HashMap<>();

		if (args.length - 1 > loc) {

			path = path + ((path.equals("") ? "" : ".") + args[loc]);

			map.put(args[loc], getHash(path, args, loc + 1, toSet));
		} else {
			map.put(args[loc], toSet);
		}

		return map;

	}

	@SuppressWarnings("unchecked")
	private void dotSet(String ref, Object toSet) {

		String[] split = ref.split("\\.");

		String path = "";

		Map<String, Object> map = null, older = null;

		for (int i = 0; i < split.length; i++) {

			// adding to the path
			path = path + ((path.equals("") ? "" : ".") + split[i]);
			Object temp = getSetting(path);

			if (temp != null && (temp instanceof Map<?, ?> && !path.equals(""))) {
				older = map;
				map = (HashMap<String, Object>) temp;
				continue;
			} else {
				if (map != null) {
					System.out.println("older = " + older);
					System.out.println("Path = " + path);
					older.remove(split[i - 1]);

					HashMap<String, Object> test = getHash(path, split, i, toSet);
					System.out.println(test);

					for (Entry<String, Object> toMerege : map.entrySet()) {
						test.put(toMerege.getKey(), toMerege.getValue());
					}

					older.put(split[i - 1], test);
				} else {
					obj.put(split[0], getHash(path, split, i, toSet));
				}
				return;
			}
		}

		System.out.println("reached");
	}

	public void saveConfiguration() {

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
	 * Uses recursion to generate a final string to be printed which represents the
	 * file
	 * 
	 * @param obj       - The map to generate the output for
	 * @param level     - The indentation level (usually leave at 0 and leave the
	 *                  recursion to change
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

	@SuppressWarnings("unchecked")
	public List<String> getStringList(String ref) {
		Object o = getSetting(ref);
		if (o instanceof ArrayList<?>) {
			return (ArrayList<String>) o;
		}
		return new ArrayList<>();
	}

	public boolean getBoolean(String ref) {
		Object o = getSetting(ref);
		if (o instanceof Boolean) {
			return (Boolean) o;
		}
		return false;
	}

	public String getString(String ref) {
		Object o = getSetting(ref);
		if (o != null) {
			return o.toString();
		}
		return "";
	}

	public int getInteger(String ref) {
		Object o = getSetting(ref);

		try {
			return Integer.parseInt(o.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	public double getDouble(String ref) {
		Object o = getSetting(ref);

		try {
			return Double.parseDouble(o.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isNull() {
		return obj == null || obj.size() == 0;
	}

}
