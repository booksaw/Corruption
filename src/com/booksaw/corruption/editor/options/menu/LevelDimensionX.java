package com.booksaw.corruption.editor.options.menu;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.meta.LevelDimensionsMeta;
import com.booksaw.corruption.level.meta.Meta;

public class LevelDimensionX extends TextOption {

	LevelDimensionsMeta m;

	@Override
	public String getTip() {
		return "Level width";
	}

	@Override
	public String getDefault() {

		for (Meta temp : LevelManager.activeLevel.metaData) {
			if (temp instanceof LevelDimensionsMeta) {
				m = (LevelDimensionsMeta) temp;
				break;
			}
		}

		return m.split[0];
	}

	@Override
	public void saveData() {

		int input;

		try {
			input = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(getDefault());
			return;
		}

		m.split[0] = input + "";
		LevelManager.activeLevel.levelDimensions.width = input;
	}

}
