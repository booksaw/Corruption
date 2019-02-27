package com.booksaw.corruption.renderControler;

import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.listeners.MenuListener;
import com.booksaw.corruption.render.GameMenu;
import com.booksaw.corruption.render.RenderInterface;

public class MenuController extends RenderController {

	MenuListener listener;
	GameMenu menu;

	@Override
	public void update(int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public RenderInterface getRenderer() {
		return (menu = new GameMenu());
	}

	@Override
	public List<Listener> generateListeners() {

		List<Listener> toReturn = new ArrayList<>();
		toReturn.add((listener = new MenuListener()));

		return toReturn;
	}

	@Override
	public List<Listener> getListeners() {
		List<Listener> toReturn = new ArrayList<>();
		toReturn.add(listener);

		return toReturn;
	}

	@Override
	public void resize() {
		menu.resize();
	}

}
