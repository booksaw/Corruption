package com.booksaw.corruption.renderControler;

import java.util.List;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.RenderInterface;

public abstract class RenderController {

	public abstract void update(int time);

	public void show() {
		Corruption.main.setActive(this);
	}

	public abstract RenderInterface getRenderer();

	public abstract List<Listener> generateListeners();

	public abstract List<Listener> getListeners();

	public abstract void resize();

	public void disable() {

	}

	public abstract void back();
}
