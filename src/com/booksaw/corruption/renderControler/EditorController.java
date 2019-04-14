package com.booksaw.corruption.renderControler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.listeners.EditorKeyListener;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.listeners.KeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.listeners.ListenerManager;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.RenderInterface;
import com.booksaw.corruption.render.overlays.EditorOverlay;
import com.booksaw.corruption.render.overlays.InteractableOverlay;
import com.booksaw.corruption.render.overlays.ObjectOverlay;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.render.overlays.SpriteOverlay;
import com.booksaw.corruption.selection.Selectable;
import com.booksaw.corruption.sprites.CameraSprite;
import com.booksaw.corruption.sprites.Sprite;

public class EditorController extends RenderController {

	GameCamera c;
	KeyListener gameListener;
	EditorMouseListener mouseListener;
	EditorKeyListener keyListener;
	CameraSprite camera;

	public EditorController() {
		camera = new CameraSprite();
		camera.setActiveplayer(true);
		c = new GameCamera(false);
	}

	@Override
	public void update(int time) {
		if (!testing)
			camera.update(time);
		else {
			LevelManager.activeLevel.update(time);
		}
	}

	@Override
	public RenderInterface getRenderer() {
		return c;
	}

	@Override
	public List<Listener> generateListeners() {
		gameListener = new KeyListener();
		mouseListener = new EditorMouseListener();
		keyListener = new EditorKeyListener();

		// important this order is used as some assumptions are made elsewhere
		List<Listener> toReturn = new ArrayList<>();

		toReturn.add(mouseListener);
		toReturn.add(keyListener);
		toReturn.add(gameListener);
		return toReturn;
	}

	@Override
	public List<Listener> getListeners() {
		List<Listener> toReturn = new ArrayList<>();
		toReturn.add(keyListener);
		toReturn.add(mouseListener);
		toReturn.add(gameListener);

		return toReturn;
	}

	@Override
	public void resize() {

	}

	@Override
	public void show() {
		super.show();
//		Corruption.main.getFrame().setResizable(false);

//		c.setSize(Corruption.origionalDimensions);
//		c.setPreferredSize(Corruption.origionalDimensions);
//		Corruption.main.getFrame().pack();
		Selectable.clearSelection();
		LevelManager lm;

		File f = new File(GameController.level);

		if (!f.exists()) {
			MenuController mc = new MenuController();
			mc.show();
			return;
		}

		lm = new LevelManager(f);

		lm.resetAllSprites();

		lm.finalise();
		lm.addSprite(camera);

		Overlay.addOverlay(new EditorOverlay());

		Corruption.main.startClock();
	}

	public void insertSprite() {
		Overlay.addOverlay(new SpriteOverlay());
	}

	public void insertObject() {
		Overlay.addOverlay(new ObjectOverlay());
	}

	public void insertInteractable() {
		Overlay.addOverlay(new InteractableOverlay());
	}

	@Override
	public void disable() {
		Corruption.main.getFrame().setResizable(true);
	}

	@Override
	public void back() {

	}

	boolean testing = false;
	KeyListener listen;

	public void toogleTestMode() {
		if (testing) {

			if (listen != null)
				ListenerManager.removeListener(listen);
			testing = false;

		} else {
			listen = new KeyListener();
			ListenerManager.addListener(listen);
			testing = true;
			Sprite s = LevelManager.activeLevel.getActivePlayer();
			s.bindToCamera(s.getX());
		}
	}

}
