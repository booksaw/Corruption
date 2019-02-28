package com.booksaw.corruption.editor.options;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.editor.options.menu.CameraX;
import com.booksaw.corruption.editor.options.menu.CameraY;
import com.booksaw.corruption.editor.options.menu.LevelColor;
import com.booksaw.corruption.editor.options.menu.LevelDimensionX;
import com.booksaw.corruption.editor.options.menu.LevelDimensionY;

public class LevelSettings extends OptionPane {

	public LevelSettings() {
		deletable = false;
	}

	@Override
	public String getName() {
		return "Level settings";
	}

	@Override
	public void loadOptions() {
		included.add(new CameraX());
		included.add(new CameraY());
		included.add(new LevelDimensionX());
		included.add(new LevelDimensionY());
		included.add(new LevelColor(Corruption.main.getFrame()));
	}

	// cannot delete level meta
	@Override
	public void deleteThing() {
	}

}
