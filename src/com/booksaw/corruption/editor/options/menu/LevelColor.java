package com.booksaw.corruption.editor.options.menu;

import javax.swing.JFrame;

import com.booksaw.corruption.editor.options.ColorPicker;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.meta.BackgroundColorMeta;
import com.booksaw.corruption.level.meta.Meta;

public class LevelColor extends ColorPicker {

	public LevelColor(JFrame f) {
		super(f);
		c = LevelManager.activeLevel.backgroundColor;
	}

	@Override
	public void saveData() {
		BackgroundColorMeta m = null;
		for (Meta temp : LevelManager.activeLevel.metaData) {
			if (temp instanceof BackgroundColorMeta) {
				m = (BackgroundColorMeta) temp;
				break;
			}
		}

		m.c = c;
		LevelManager.activeLevel.backgroundColor = c;

	}

	@Override
	public String getTip() {
		return "Background Color";
	}

}
