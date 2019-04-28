package com.booksaw.corruption.renderControler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.trigger.Trigger;
import com.booksaw.corruption.listeners.GameMouseListener;
import com.booksaw.corruption.listeners.KeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.RenderInterface;
import com.booksaw.corruption.render.overlays.GameOverlay;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.selection.Selectable;

public class GameController extends RenderController {

	public static GameController gameController;

	/**
	 * The listener so we can find out what buttons are being pressed
	 */
	private KeyListener keyListener;
	private GameMouseListener mouseListener;

	public GameCamera c;
	private GameOverlay gameOverlay;
	public static String level = "1.level";

	public GameController() {
		gameController = this;
		c = new GameCamera(true);
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
			JOptionPane.showMessageDialog(Corruption.main.getFrame(), "Cannot find level", Language.getMessage("title"),
					JOptionPane.PLAIN_MESSAGE, Config.logo);
			mc.show();
			return;
		}

		lm = new LevelManager(f);

		lm.finalise();

		Overlay.addOverlay((gameOverlay = new GameOverlay()));
		CursorManager.hideCursor();

		Trigger.showTriggers = false;
		Corruption.main.startClock();

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
		toReturn.add(keyListener);
		toReturn.add(mouseListener);

		return toReturn;
	}

	/**
	 * around code possibly constructor stuff in RenderInterface
	 */
	public List<Listener> generateListeners() {
		keyListener = new KeyListener();
		mouseListener = new GameMouseListener();

		List<Listener> toReturn = new ArrayList<>();
		toReturn.add(keyListener);
		toReturn.add(mouseListener);

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
		CursorManager.resetCursor();
	}

}
