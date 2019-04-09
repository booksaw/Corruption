package com.booksaw.corruption.render.overlays.menu;

import com.booksaw.corruption.renderControler.GameController;
import com.booksaw.corruption.renderControler.MenuController;

public class LevelCompleteOverlay extends MenuOverlay {

	@Override
	public void addItems() {
		components.add(new MenuComponent(this, "Level Complete", "Level Complete", false));

		components.add(new MenuComponent(this, "Next Level", "next", true));
		components.add(new MenuComponent(this, "Main Menu", "menu", true));
	}

	@Override
	public void activate() {

		switch (getSelected().getReference()) {
		case "next":
			GameController.gameController.show();
			break;
		case "menu":
			MenuController c = new MenuController();
			c.show();
		}

	}

}
