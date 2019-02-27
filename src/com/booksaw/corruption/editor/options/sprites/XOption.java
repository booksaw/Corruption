package com.booksaw.corruption.editor.options.sprites;

import com.booksaw.corruption.sprites.Sprite;

public class XOption extends SpriteOption {

	public XOption(Sprite s) {
		super(s);
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
