package com.booksaw.corruption.editor.options.menu;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.meta.CameraLocationMeta;
import com.booksaw.corruption.level.meta.Meta;

public class CameraY extends TextOption {

	CameraLocationMeta m;

	@Override
	public String getTip() {
		return "Starting camera Y";
	}

	@Override
	public String getDefault() {

		for (Meta temp : LevelManager.activeLevel.metaData) {
			if (temp instanceof CameraLocationMeta) {
				m = (CameraLocationMeta) temp;
				break;
			}
		}

		return m.split[1];
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

		m.split[1] = input + "";
	}
}
