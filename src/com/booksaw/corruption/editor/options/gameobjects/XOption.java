package com.booksaw.corruption.editor.options.gameobjects;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.objects.GameObject;

public class XOption extends TextOption {

	GameObject b;

	public XOption(GameObject b) {
		this.b = b;
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(b.getX() + "");
			return;
		}

		b.setX(value);
	}

	@Override
	public String getTip() {
		return "X Co-ord";
	}

	@Override
	public String getDefault() {
		return b.getX() + "";
	}
}
