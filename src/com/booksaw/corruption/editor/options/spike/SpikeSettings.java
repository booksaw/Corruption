package com.booksaw.corruption.editor.options.spike;

import com.booksaw.corruption.editor.options.OptionPane;
import com.booksaw.corruption.editor.options.location.XOption;
import com.booksaw.corruption.editor.options.location.YOption;
import com.booksaw.corruption.level.objects.Spike;

public class SpikeSettings extends OptionPane {

	Spike s;

	public SpikeSettings(Spike s) {
		this.s = s;
		intialize();
	}

	@Override
	public String getName() {
		return "Spike Settings";
	}

	@Override
	public void loadOptions() {
		included.add(new XOption(s));
		included.add(new YOption(s));
		included.add(new DirectionOption(s));

	}

	@Override
	public void deleteThing() {
		s.delete();
	}

}
