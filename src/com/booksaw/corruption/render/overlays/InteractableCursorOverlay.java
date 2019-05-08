package com.booksaw.corruption.render.overlays;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.interactable.Interactable;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.render.GameCamera;

public class InteractableCursorOverlay extends Overlay {

	public static InteractableCursorOverlay interactableOverlay;
	public Interactable i;

	public InteractableCursorOverlay(Interactable i) {
		CursorManager.hideCursor();
		this.i = i;
		interactableOverlay = this;
		EditorMouseListener.selection = ActiveSelection.INTERACTABLECURSOR;

	}

	@Override
	public void render(Graphics g) {

		Point p = MouseInfo.getPointerInfo().getLocation();
		Container f = Corruption.main.getFrame().getContentPane();
		p.y = f.getLocationOnScreen().y - p.y + GameCamera.cameraHeight;
		p.x = p.x - f.getLocationOnScreen().x;
		i.setLocation(p);

		i.paint(g, new Rectangle(0, 0, GameCamera.cameraWidth, GameCamera.cameraHeight));

	}

	@Override
	public void hide() {
		CursorManager.resetCursor();
		EditorMouseListener.selection = ActiveSelection.MAIN;
		LevelManager.activeLevel.removeInteractable(i);
	}

	@Override
	public void resize() {

	}

}
