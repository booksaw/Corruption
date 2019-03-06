package com.booksaw.corruption.editor.options;

import com.booksaw.corruption.editor.options.door.OpenOption;
import com.booksaw.corruption.editor.options.location.XOption;
import com.booksaw.corruption.editor.options.location.YOption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.objects.Door;

public class DoorSettings extends OptionPane {

	Door d;

	public DoorSettings(Door d) {
		super();
		this.d = d;
		
		intialize();
	}

	@Override
	public String getName() {
		return "Door settings";
	}

	@Override
	public void loadOptions() {
		included.add(new XOption(d));
		included.add(new YOption(d));
		included.add(new OpenOption(d));
		
	}

	@Override
	public void deleteThing() {
		LevelManager.activeLevel.removeObject(d);
	}

}
