package com.booksaw.corruption.level.save;

import java.util.ArrayList;
import java.util.List;

public class Change {


	private List<SingleChange> change = new ArrayList<>();

	SaveManager saveManager;
	
	public Change(SaveManager saveManager) {
		this.saveManager = saveManager;
	}

	public void addChange(ChangeType type, int location, String line) {
		change.add(new SingleChange(type, line, location));
		
	
	}

	public void revert(List<String> levelInfo) {
		for (SingleChange temp : change) {
			switch (temp.type) {
			case ADD:
				levelInfo.remove(temp.location);
				break;
			case REMOVE:
				levelInfo.set(temp.location, temp.info);
			}
		}
	}
	
	public boolean hasChanged() {
		if(change.size() != 0) {
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
		int location;

		public SingleChange(ChangeType type, String info, int location) {
			this.info = info;
			this.location = location;
			this.type = type;
		}
	}

}
