package com.booksaw.corruption.level.save;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.booksaw.corruption.configuration.YamlConfiguration;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.background.Background;
import com.booksaw.corruption.level.interactable.Interactable;
import com.booksaw.corruption.level.meta.Meta;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.sprites.Sprite;

public class SaveManager {

	LevelManager level;
	Stack<Change> history = new Stack<>();
	List<String> levelInfo = new ArrayList<>();
	YamlConfiguration config;

	// details read from the level file
	String nextLevel;

	public SaveManager(LevelManager level) {
		this.level = level;

		config = new YamlConfiguration(level.getF());

		nextLevel = config.getString("nextLevel");

		load();

	}

	public void load() {
		levelInfo = config.getStringList("level");

		if (levelInfo == null || levelInfo.size() == 0) {
			level.resetLevel();
			return;
		}

		for (String str : levelInfo) {
			level.runLine(str);
		}

	}

	public void undo() {

		history.pop().revert(levelInfo);

	}

	/*
	 * public void redo() { // TODO }
	 */

	public void changes() {

		detectChanges(levelInfo);

	}

	public void save() {
		List<String> levelInfo = new ArrayList<>();

		for (Meta m : level.getMetaData()) {
			String s = m.toString();
			if (!s.equals(""))
				levelInfo.add(s);
		}

		for (GameObject o : level.getLevelObjects()) {
			if (o.needsSaving()) {
				String s = o.toString();
				if (!s.equals(""))
					levelInfo.add(s);
			}
		}

		for (Background b : level.getBackgrounds()) {
			String s = b.toString();
			if (!s.equals("") && b.needsSaving())
				levelInfo.add(s);
		}

		for (Sprite s : level.getSprites()) {
			String st = s.toString();
			if (!st.equals(""))
				levelInfo.add(st);
		}

		for (Interactable i : level.getInteractables()) {
			String s = i.toString();
			if (!s.equals("")) {
				levelInfo.add(s);
			}
		}

		level.setChanged(false);
		config.set("level", level);
		config.set("nextLevel", nextLevel);

		config.saveConfiguration();
	}

	private void saveToLevelInfo() {
		// TODO
	}

	private Change detectChanges(List<String> levelInfo) {
		return null;
		// TODO
	}

	public String getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}

}
