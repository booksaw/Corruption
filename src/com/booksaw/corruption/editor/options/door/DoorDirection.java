package com.booksaw.corruption.editor.options.door;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.level.objects.Door;

public class DoorDirection extends MessageOption {

	Door d;
	JCheckBox cb;

	public DoorDirection(Door d) {
		this.d = d;
	}

	@Override
	public void saveData() {
		d.setLeft(cb.isSelected());
	}

	@Override
	public String getTip() {
		return "Left";
	}

	@Override
	public JComponent getInput() {

		cb = new JCheckBox();
		cb.setSelected(d.isLeft());
		return cb;
	}

}
