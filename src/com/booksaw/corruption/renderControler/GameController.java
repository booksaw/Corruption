package com.booksaw.corruption.renderControler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.listeners.KeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.RenderInterface;
import com.booksaw.corruption.render.overlays.GameOverlay;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.render.overlays.SpeechBubble;
import com.booksaw.corruption.selection.Selectable;

public class GameController extends RenderController {

	public static GameController gameController;

	/**
	 * The listener so we can find out what buttons are being pressed
	 */
	private KeyListener listener;

	public GameCamera c;
	private GameOverlay gameOverlay;
	public static String level = "1.level";

	public GameController() {
		gameController = this;
		c = new GameCamera(true);
		level = "1.level";
	}

	public GameController(String level) {
		gameController = this;
		c = new GameCamera(true);
		GameController.level = level;
	}

	@Override
	public void update(int time) {

		LevelManager.activeLevel.update(time);
	}

	@Override
	public void show() {
		super.show();
		Selectable.clearSelection();

		LevelManager lm;

		File f = new File(level);

		if (!f.exists()) {
			MenuController mc = new MenuController();
			mc.show();
			return;
		}

		lm = new LevelManager(f);

		lm.finalise();

		Overlay.addOverlay((gameOverlay = new GameOverlay()));

		Corruption.main.startClock();

		Overlay.addOverlay(
				new SpeechBubble(LevelManager.activeLevel.getSprites().get(0), "This is a test of true text"));

	}

	@Override
	public RenderInterface getRenderer() {
		return c;
	}

	/**
	 * So other people can get the listener
	 * 
	 * @return
	 */
	public List<Listener> getListeners() {
		List<Listener> toReturn = new ArrayList<>();
		toReturn.add(listener);

		return toReturn;
	}

	/**
	 * around code possibly constructor stuff in RenderInterface
	 */
	public List<Listener> generateListeners() {
		listener = new KeyListener();

		List<Listener> toReturn = new ArrayList<>();
		toReturn.add(listener);

		return toReturn;
	}

	@Override
	public void resize() {
		c.resize();
	}

	@Override
	public void back() {

	}

	@Override
	public void disable() {
		Overlay.removeOverlay(gameOverlay);
	}

}
