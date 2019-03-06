package com.booksaw.corruption.editor.options.location;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.Location;

public class XOption extends TextOption {

	Location s;

	public XOption(Location s) {
		this.s = s;
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(s.getX() + "");
			return;
		}

		s.setX(value);
	}

	@Override
	public String getTip() {
		return "X Co-ord";
	}

	@Override
	public String getDefault() {
		return s.getX() + "";
	}
}
