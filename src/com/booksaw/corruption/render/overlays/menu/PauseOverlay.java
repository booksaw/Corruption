package com.booksaw.corruption.render.overlays.menu;

import javax.swing.JOptionPane;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.MenuController;

public class PauseOverlay extends MenuOverlay {

	public static boolean paused = false;
	
	@Override
	public void addItems() {

		components.add(new MenuComponent(this, Language.getMessage("pause.resume"), "resume"));

		String text;
		if (Corruption.main.controller instanceof EditorController) {
			text = Language.getMessage("pause.savequit");
		} else {
			text = Language.getMessage("pause.quit");
		}

		components.add(new MenuComponent(this, text, "quit"));

	}

	@Override
	public void activate() {

		switch (getSelected().getReference()) {
		case "resume":
			Overlay.removeOverlay(this);
			break;
		case "quit":
			if (LevelManager.activeLevel.hasChanged() && Corruption.main.controller instanceof EditorController) {
				int result = JOptionPane.showConfirmDialog(Corruption.main.getFrame(),
						Language.getMessage("pause.save"), Language.getMessage("title"),
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, Config.logo);
				if (result == 0) {
					LevelManager.activeLevel.save();
				} else if (result == -1 || result == 2) {
					return;
				}

			}

			Overlay.removeOverlay(this);
			Corruption.main.setActive(new MenuController());
			break;
		}

	}

	@Override
	public void show() {
		super.show();
		paused = true;
	}
	
	@Override
	public void hide() {
		super.hide();
		paused = false; 
	}
	
}
