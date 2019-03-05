package com.booksaw.corruption.editor.options.gameobjects;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.objects.GameObject;

public class YOption extends TextOption {

	GameObject b;
	
	public YOption(GameObject b) {
		this.b = b;
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(b.getY() + "");
			return;
		}

		b.setY(value);
	}

	@Override
	public String getTip() {
		return "Y Co-ord";
	}

	@Override
	public String getDefault() {
		return b.getY() + "";
	}
}
