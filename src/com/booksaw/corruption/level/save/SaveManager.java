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
import com.booksaw.corruption.level.save.Change.ChangeType;
import com.booksaw.corruption.sprites.Sprite;

public class SaveManager {

	LevelManager level;
	// stack is best option as last change is the first undone
	Stack<Change> history = new Stack<>();
	Stack<Change> redo = new Stack<>();

	List<String> levelInfo = new ArrayList<>();
	public YamlConfiguration config;

	// details read from the level file
	String nextLevel;
	boolean changes = false;

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

		if (history.isEmpty()) {
			return;
		}

		Change c = history.pop();
		c.revert(levelInfo);

		level.resetLists();

		for (String str : levelInfo) {
			level.runLine(str);
		}

		c.invert();
		redo.push(c);
		changes = true;

	}

	public void redo() {
		if (redo.isEmpty()) {
			return;
		}

		Change c = redo.pop();
		c.revert(levelInfo);

		level.resetLists();

		for (String str : levelInfo) {
			level.runLine(str);
		}

		c.invert();
		history.push(c);
		changes = true;

	}

	public void changes() {
		List<String> newLevelInfo = getLevelInfo();

		Change c = detectChanges(levelInfo, newLevelInfo);

		if (c == null || !c.hasChanged()) {
			return;
		}

//		redo = new Stack<>();
		history.push(c);
		changes = true;
		levelInfo = newLevelInfo;

	}

	public void save() {

		level.setChanged(false);
		config.set("level", levelInfo);
		config.saveConfiguration();
		changes = false;
	}

	private List<String> getLevelInfo() {
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

		return levelInfo;

	}

	private Change detectChanges(List<String> prior, List<String> levelInfo) {

		Change c = new Change();

		for (String str : levelInfo) {
			if (!prior.contains(str)) {
				c.addChange(ChangeType.ADD, str);
			}
		}

		for (String str : prior) {
			if (!levelInfo.contains(str)) {
				c.addChange(ChangeType.REMOVE, str);
			}
		}

		return c;
	}

	public String getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}

	public boolean hasChanged() {
		return changes;
	}

}
