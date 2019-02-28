package com.booksaw.corruption.editor.options.sprites;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.sprites.Sprite;

public class YOption extends TextOption {

	Sprite s;
	
	public YOption(Sprite s) {
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
