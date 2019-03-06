package com.booksaw.corruption.editor.options.dimensions;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.Dimensions;

public class HeightOption extends TextOption  {

	Dimensions b;
	
	public HeightOption(Dimensions d) {
		this.b = d; 
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
