package com.booksaw.corruption.level.save;

import java.util.ArrayList;
import java.util.List;

public class Change {

	private List<SingleChange> change = new ArrayList<>();

	public void addChange(ChangeType type, String line) {
		change.add(new SingleChange(type, line));

	}

	public void revert(List<String> levelInfo) {
		for (SingleChange temp : change) {
			switch (temp.type) {
			case ADD:
				levelInfo.remove(temp.info);
				break;
			case REMOVE:
				levelInfo.add(temp.info);
			}
		}
	}

	public boolean hasChanged() {
		System.out.println(change.size());
		if (change.size() != 0) {
			return true;
		}
		return false;
	}

	public enum ChangeType {
		ADD, REMOVE;
	}

	public class SingleChange {

		ChangeType type;
		String info;

		public SingleChange(ChangeType type, String info) {
			this.info = info;
			this.type = type;
		}
	}

	public void invert() {

		for (SingleChange temp : change) {
			temp.type = (temp.type == ChangeType.ADD) ? ChangeType.REMOVE : ChangeType.ADD;
		}

	}

}
