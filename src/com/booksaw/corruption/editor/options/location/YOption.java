package com.booksaw.corruption.editor.options.location;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.Location;

public class YOption extends TextOption {

	Location s;

	public YOption(Location s) {
		this.s = s;
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(s.getY() + "");
			return;
		}

		s.setY(value);
	}

	@Override
	public String getTip() {
		return "Y Co-ord";
	}

	@Override
	public String getDefault() {
		return s.getY() + "";
	}
}
