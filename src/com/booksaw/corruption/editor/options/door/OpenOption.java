package com.booksaw.corruption.editor.options.door;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.level.objects.Door;

public class OpenOption extends MessageOption {

	JCheckBox box;
	Door d;

	public OpenOption(Door d) {
		this.d = d;
	}

	@Override
	public void saveData() {
		d.setOpen(box.isSelected());
	}

	@Override
	public String getTip() {
		return "open";
	}

	@Override
	public JComponent getInput() {

		box = new JCheckBox();
		box.setSelected(d.isStartingOpen());

		return box;
	}

}
