package com.booksaw.corruption.editor.options.background;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.background.Background;

public class HeightOption extends TextOption  {

	Background b;
	
	public HeightOption(Background background) {
		this.b = background; 
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(b.getHeight() + "");
			return;
		}

		b.setHeight(value);
	}

	@Override
	public String getTip() {
		return "Height";
	}

	@Override
	public String getDefault() {
		return b.getHeight() + "";
	}
	

}
