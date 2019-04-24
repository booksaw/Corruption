package com.booksaw.corruption.editor.options.execution.executionOption;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.UUID;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.booksaw.corruption.editor.options.execution.ExecutionOption;
import com.booksaw.corruption.editor.options.execution.SetOptions;
import com.booksaw.corruption.execution.CommandList;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.selection.Selectable;
import com.booksaw.corruption.sprites.Sprite;

public class KillExecution extends ExecutionOption {

	Sprite selection;

	public KillExecution(JFrame f, String[] information, SetOptions set) {
		super(f, information, set);
	}

	@Override
	protected JPanel generatePanel() {
		JPanel p = new JPanel(new GridLayout(1, 0));
		JComponent c = getCommandSelector(CommandList.KILL);
		p.add(getRemove());
		p.add(c);

		if (information.length > 0) {
			selection = (Sprite) Selectable.getSelectable(UUID.fromString(information[0]));
			setSelected(selection);
		}
		p.add(getSelectableSelector());

		return p;
	}

	@Override
	public String toSave() {
		if (selection == null) {
			return "";
		}

		return "kill:" + selection.uuid;
	}

	@Override
	public void clickFind(Point p) {
		Sprite s = Sprite.getSprite(p, LevelManager.activeLevel.getSprites());
		if (s != null) {
			selection = s;
			setSelected(s);
		}

	}
}
