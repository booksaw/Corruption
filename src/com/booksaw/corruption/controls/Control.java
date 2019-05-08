package com.booksaw.corruption.controls;

public class Control {

	String name;
	int[] keys;

	public Control(String name, String string) {

		String[] split = string.split(";");

		keys = new int[split.length];

		for (int i = 0; i < split.length; i++) {
			keys[i] = Integer.parseInt(split[i]);
		}

		this.name = name;
	}

	@Override
	public String toString() {

		String toReturn = "";

		for (int i = 0; i < keys.length; i++) {
			toReturn = toReturn + keys[i] + ((keys.length - 1 == i) ? "" : ";");
		}

		return toReturn;
	}

}
