package com.booksaw.corruption.editor.options.spike;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.level.objects.Spike;
import com.booksaw.corruption.level.objects.Spike.Direction;

public class DirectionOption extends MessageOption {

	Spike s;
	JComboBox<Direction> cb;

	public DirectionOption(Spike s) {
		this.s = s;

	}

	@Override
	public void saveData() {

		s.setDirection((Direction) cb.getSelectedItem());
	}

	@Override
	public String getTip() {
		return "Direction";
	}

	@Override
	public JComponent getInput() {

		cb = new JComboBox<>(Direction.values());
		cb.setSelectedItem(s.getDirection());
		return cb;
	}

}
