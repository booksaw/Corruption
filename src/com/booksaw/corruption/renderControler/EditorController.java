package com.booksaw.corruption.renderControler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.listeners.KeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.RenderInterface;
import com.booksaw.corruption.render.overlays.EditorOverlay;
import com.booksaw.corruption.render.overlays.ObjectOverlay;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.render.overlays.SpriteOverlay;
import com.booksaw.corruption.sprites.CameraSprite;

public class EditorController extends RenderController {

	GameCamera c;
	KeyListener keyListener;
	EditorMouseListener mouseListener;
	CameraSprite camera;

	public EditorController() {

		camera = new CameraSprite();
		camera.setActiveplayer(true);
		c = new GameCamera();
	}

	@Override
	public void update(int time) {
		camera.update(time);

//		LevelManager.activeLevel.update(time);

	}

	@Override
	public RenderInterface getRenderer() {
		return c;
	}

	@Override
	public List<Listener> generateListeners() {
		keyListener = new KeyListener();
		mouseListener = new EditorMouseListener();

		List<Listener> toReturn = new ArrayList<>();
		toReturn.add(keyListener);
		toReturn.add(mouseListener);

		return toReturn;
	}

	@Override
	public List<Listener> getListeners() {
		List<Listener> toReturn = new ArrayList<>();
		toReturn.add(keyListener);
		toReturn.add(mouseListener);

		return toReturn;
	}

	@Override
	public void resize() {

	}

	@Override
	public void show() {
		super.show();
		Corruption.main.getFrame().setResizable(false);
		Corruption.main.getFrame().getContentPane().setPreferredSize(Corruption.origionalDimensions);
		Corruption.main.getFrame().getContentPane().setSize(Corruption.origionalDimensions);
		Corruption.main.getFrame().pack();

		LevelManager lm;
		if (LevelManager.activeLevel == null) {
			lm = new LevelManager(new File("1.level"));

		} else {
			lm = LevelManager.activeLevel;
			lm.load();
		}

		lm.resetAll();

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

	@Override
	public void disable() {
		Corruption.main.getFrame().setResizable(true);
	}

	@Override
	public void back() {

	}

}
