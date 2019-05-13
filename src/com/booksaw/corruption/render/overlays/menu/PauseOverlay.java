package com.booksaw.corruption.render.overlays.menu;

import javax.swing.JOptionPane;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.MenuController;
import com.booksaw.corruption.settings.Settings;

public class PauseOverlay extends MenuOverlay {

	public static boolean paused = false;

	@Override
	public void addItems() {

		components.add(new MenuComponent(this, Language.getMessage("pause.resume"), "resume", true));

		String text;
		if (Corruption.main.controller instanceof EditorController) {
			text = Language.getMessage("pause.savequit");
		} else {
			text = Language.getMessage("pause.quit");
		}

		components.add(new MenuComponent(this, text, "quit", true));
		components.add(new MenuComponent(this, Language.getMessage("menu.settings"), "settings", true));

	}

	@Override
	public void activate() {

		switch (getSelected().getReference()) {
		case "resume":
			Overlay.removeOverlay(this);
			break;
		case "quit":
			if (LevelManager.activeLevel.getSaveManager().hasChanged()
					&& Corruption.main.controller instanceof EditorController) {
				int result = JOptionPane.showConfirmDialog(Corruption.main.getFrame(),
						Language.getMessage("pause.save"), Language.getMessage("title"),
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, Config.logo);
				if (result == 0) {
					LevelManager.activeLevel.getSaveManager().save();
				} else if (result == -1 || result == 2) {
					return;
				}

			}

			Overlay.removeOverlay(this);
			Corruption.main.setActive(new MenuController());
			break;
		case "settings":
			Settings.displaySettings();
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
