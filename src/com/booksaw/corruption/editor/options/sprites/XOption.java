package com.booksaw.corruption.editor.options.sprites;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.sprites.Sprite;

public class XOption extends TextOption {

	Sprite s;

	public XOption(Sprite s) {
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
