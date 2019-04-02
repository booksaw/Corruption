package com.booksaw.corruption.listeners;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.editor.options.ColorPicker;
import com.booksaw.corruption.editor.options.LevelSettings;
import com.booksaw.corruption.editor.options.background.BackgroundSettings;
import com.booksaw.corruption.editor.options.cursor.CursorSettings;
import com.booksaw.corruption.editor.options.cursor.CursorSettings.SELECTION;
import com.booksaw.corruption.editor.options.door.DoorSettings;
import com.booksaw.corruption.editor.options.gameobjects.BlockSettings;
import com.booksaw.corruption.editor.options.spike.SpikeSettings;
import com.booksaw.corruption.editor.options.sprites.SpriteSettings;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.background.Background;
import com.booksaw.corruption.level.background.ColoredBackground;
import com.booksaw.corruption.level.background.DraggedBackground;
import com.booksaw.corruption.level.interactable.Interactable;
import com.booksaw.corruption.level.objects.Block;
import com.booksaw.corruption.level.objects.Door;
import com.booksaw.corruption.level.objects.DraggedBlock;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.level.objects.ObjectList;
import com.booksaw.corruption.level.objects.Spike;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.overlays.ActiveSelection;
import com.booksaw.corruption.render.overlays.EditorOverlay;
import com.booksaw.corruption.render.overlays.InteractableCursorOverlay;
import com.booksaw.corruption.render.overlays.InteractableOverlay;
import com.booksaw.corruption.render.overlays.ObjectCursorOverlay;
import com.booksaw.corruption.render.overlays.ObjectOverlay;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.render.overlays.SpriteCursorOverlay;
import com.booksaw.corruption.render.overlays.SpriteOverlay;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.RenderController;
import com.booksaw.corruption.selection.DraggedSelection;
import com.booksaw.corruption.selection.Selectable;
import com.booksaw.corruption.sprites.Sprite;

public class EditorMouseListener implements Listener, MouseListener, MouseMotionListener {

	public static ActiveSelection selection = ActiveSelection.MAIN;

	@Override
	public void mouseDragged(MouseEvent e) {

		if (selection == ActiveSelection.SPRITE || selection == ActiveSelection.SPRITECURSOR
				|| selection == ActiveSelection.OBJECT || selection == ActiveSelection.OBJECTCURSOR
				|| selection == ActiveSelection.INTERACTABLE || selection == ActiveSelection.INTERACTABLECURSOR
				|| selection == ActiveSelection.EYEDROPPER) {
			return;
		}

		Selectable s = Selectable.getSelectable(e.getPoint());
		if (s != null) {
			s.drag(e.getPoint());
			return;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (CursorSettings.selection == SELECTION.BLOCK) {

				if (DraggedBlock.block == null) {
					return;
				}
				DraggedBlock.block.setPoint(e.getPoint());
			} else if (CursorSettings.selection == SELECTION.BACKGROUND) {

				if (DraggedBackground.background == null) {
					return;
				}
				DraggedBackground.background.setPoint(e.getPoint());
			}
		} else {

			if (DraggedSelection.selection == null) {
				return;
			}
			DraggedSelection.selection.setPoint(e.getPoint());

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (selection == ActiveSelection.EYEDROPPER) {
			return;
		}

		Selectable s = Selectable.getSelectable(e.getPoint());
		if (s == null) {
			if (!CursorManager.normal) {
				CursorManager.resetCursor();
			}
			return;
		}

		s.hover(e.getPoint());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (selection == ActiveSelection.SPRITE || selection == ActiveSelection.SPRITECURSOR
				|| selection == ActiveSelection.OBJECT || selection == ActiveSelection.OBJECTCURSOR
				|| selection == ActiveSelection.INTERACTABLE || selection == ActiveSelection.INTERACTABLECURSOR
				|| selection == ActiveSelection.EYEDROPPER) {
			return;
		}

		if (SwingUtilities.isLeftMouseButton(e)) {

			Selectable s = Selectable.getSelectable(e.getPoint());
			if (s != null) {
				s.click(e.getPoint());
				return;
			}
			if (CursorSettings.selection == SELECTION.BLOCK) {
				new DraggedBlock(e.getPoint());
			} else if (CursorSettings.selection == SELECTION.BACKGROUND) {
				new DraggedBackground(e.getPoint());
			}

		} else {
			new DraggedSelection(e.getPoint());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		Selectable s = Selectable.getSelectable(e.getPoint());
		if (s != null && SwingUtilities.isLeftMouseButton(e)) {
			s.release(e.getPoint());
			return;
		}

		Point p = Utils.getScaledPoint(e.getPoint(), GameCamera.activeCamera.getSize());
		switch (selection) {
		case MAIN:
			mainClick(e, p);
			break;
		case SPRITE:
			spriteClick(p);
			break;
		case SPRITECURSOR:
			spriteCursorClick(e, p);
			break;
		case OBJECT:
			ObjectClick(p);
			break;
		case OBJECTCURSOR:
			ObjectCursorClick(e, p);
			break;
		case INTERACTABLE:
			InteractableClick(p);
			break;
		case INTERACTABLECURSOR:
			InteractableCursorClick(e, p);
			break;
		case EYEDROPPER:

			try {
				Robot r = new Robot();

				ColorPicker.p.click(r.getPixelColor(e.getXOnScreen(), e.getYOnScreen()));

			} catch (AWTException e1) {
				break;
			}

			break;
		}
		if (DraggedSelection.selection != null)
			DraggedSelection.selection.finalise();

	}

	public void save() {
		LevelManager.activeLevel.save();
	}

	public void insertSprite() {

		RenderController temp = Corruption.main.controller;

		if (!(temp instanceof EditorController)) {
			return;
		}

		((EditorController) temp).insertSprite();

	}

	public void insertObject() {

		RenderController temp = Corruption.main.controller;

		if (!(temp instanceof EditorController)) {
			return;
		}

		((EditorController) temp).insertObject();

	}

	public void insertInteractable() {

		RenderController temp = Corruption.main.controller;

		if (!(temp instanceof EditorController)) {
			return;
		}

		((EditorController) temp).insertInteractable();

	}

	private void mainClick(MouseEvent e, Point p) {
		if (EditorOverlay.activeOverlay.isShowing) {
			if (p.getX() > GameCamera.cameraWidth - EditorOverlay.SQUARE
					&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE && p.getX() < GameCamera.cameraWidth
					&& p.getY() < GameCamera.cameraHeight) {
				save();
				return;
			}

			if (p.getX() > GameCamera.cameraWidth - (EditorOverlay.SQUARE * 2)
					&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE
					&& p.getX() < GameCamera.cameraWidth - EditorOverlay.SQUARE && p.getY() < GameCamera.cameraHeight) {
				insertSprite();
				return;
			}

			if (p.getX() > GameCamera.cameraWidth - (EditorOverlay.SQUARE * 3)
					&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE
					&& p.getX() < GameCamera.cameraWidth - (EditorOverlay.SQUARE * 2)
					&& p.getY() < GameCamera.cameraHeight) {

				LevelSettings settings = new LevelSettings();
				settings.intialize();
				settings.setVisible(true);
				return;
			}
			if (p.getX() > GameCamera.cameraWidth - (EditorOverlay.SQUARE * 4)
					&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE
					&& p.getX() < GameCamera.cameraWidth - (EditorOverlay.SQUARE * 3)
					&& p.getY() < GameCamera.cameraHeight) {

				CursorSettings settings = new CursorSettings();
				settings.intialize();
				settings.setVisible(true);
				return;
			}
			if (p.getX() > GameCamera.cameraWidth - (EditorOverlay.SQUARE * 5)
					&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE
					&& p.getX() < GameCamera.cameraWidth - (EditorOverlay.SQUARE * 4)
					&& p.getY() < GameCamera.cameraHeight) {

				int result = JOptionPane.showConfirmDialog(Corruption.main.getFrame(),
						Language.getMessage("editor.trash"), Language.getMessage("title"), JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, Config.logo);

				if (result == 0) {
					LevelManager.activeLevel.erase();
				}

				return;
			}
			if (p.getX() > GameCamera.cameraWidth - (EditorOverlay.SQUARE * 6)
					&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE
					&& p.getX() < GameCamera.cameraWidth - (EditorOverlay.SQUARE * 5)
					&& p.getY() < GameCamera.cameraHeight) {

				insertObject();
				return;
			}
			if (p.getX() > GameCamera.cameraWidth - (EditorOverlay.SQUARE * 7)
					&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE
					&& p.getX() < GameCamera.cameraWidth - (EditorOverlay.SQUARE * 6)
					&& p.getY() < GameCamera.cameraHeight) {

				insertInteractable();
				return;
			}

		}

		if (CursorSettings.selection == SELECTION.BLOCK) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (DraggedBlock.block == null) {
					mainClickFinalize(e, e.getPoint());
					return;
				}
				if (DraggedBlock.block.getWidth() == 0 || DraggedBlock.block.getHeight() == 0) {
					mainClickFinalize(e, e.getPoint());
					DraggedBlock.block = null;
					return;
				}
				DraggedBlock.block.finalise();
				return;
			}

		} else if (CursorSettings.selection == SELECTION.BACKGROUND) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (DraggedBackground.background == null) {
					mainClickFinalize(e, e.getPoint());
					return;
				}
				if (DraggedBackground.background.getWidth() == 0 || DraggedBackground.background.getHeight() == 0) {
					mainClickFinalize(e, e.getPoint());
					DraggedBackground.background = null;
					return;
				}
				DraggedBackground.background.finalise();
				return;
			}
		} else if (CursorSettings.selection == SELECTION.SELECTOR) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (DraggedSelection.selection == null) {
					mainClickFinalize(e, e.getPoint());
					return;
				}
				if (DraggedSelection.selection.getWidth() == 0 || DraggedSelection.selection.getHeight() == 0) {
					mainClickFinalize(e, e.getPoint());
					DraggedSelection.selection = null;
					return;
				}
				DraggedSelection.selection.finalise();
				return;
			}
		}

		if (SwingUtilities.isRightMouseButton(e)) {
			mainClickFinalize(e, e.getPoint());
			return;
		}
	}

	private void mainClickFinalize(MouseEvent e, Point p) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClickFinalize(p);
			return;
		}
		Point temp = new Point(p.x + GameCamera.activeCamera.x,
				GameCamera.cameraHeight - (p.y + GameCamera.activeCamera.y));

		GameObject o = GameObject.getObject(temp);
		if (o != null) {
			if (o instanceof Block) {
				new BlockSettings((Block) o).setVisible(true);
				return;
			} else if (o instanceof Door) {
				new DoorSettings((Door) o).setVisible(true);
				return;
			} else if (o instanceof Spike) {
				new SpikeSettings((Spike) o).setVisible(true);
				return;
			}
		}

		Sprite s = Sprite.getSprite(temp, LevelManager.activeLevel.getSprites());
		if (s != null) {
			new SpriteSettings(s).setVisible(true);
			return;
		}

		Background b = Background.getBackground(temp, LevelManager.activeLevel.getBackgrounds());
		if (b != null && (b instanceof ColoredBackground)) {
			new BackgroundSettings((ColoredBackground) b).setVisible(true);
			return;
		}
	}

	private void spriteClick(Point p) {
		Overlay store = null;
		for (Overlay o : Overlay.getActiveOverlays()) {
			if (!(o instanceof SpriteOverlay)) {
				continue;
			}

			store = o;
			((SpriteOverlay) o).click(p);
			break;
		}

		Overlay.removeOverlay(store);

	}

	private void spriteCursorClick(MouseEvent e, Point p) {
		if (SwingUtilities.isLeftMouseButton(e)) {

			selection = ActiveSelection.MAIN;
			SpriteCursorOverlay.cursorOverlay.s
					.setX(SpriteCursorOverlay.cursorOverlay.s.getX() + GameCamera.activeCamera.x);
			Overlay.removeOverlay(SpriteCursorOverlay.cursorOverlay);
			SpriteCursorOverlay.cursorOverlay.s.setStartingLocation();
			LevelManager.activeLevel.addSprite(SpriteCursorOverlay.cursorOverlay.s);

		} else if (SwingUtilities.isRightMouseButton(e)) {
			selection = ActiveSelection.MAIN;
			Overlay.removeOverlay(SpriteCursorOverlay.cursorOverlay);
		}
	}

	private void ObjectClick(Point p) {
		Overlay store = null;
		for (Overlay o : Overlay.getActiveOverlays()) {
			if (!(o instanceof ObjectOverlay)) {
				continue;
			}

			store = o;
			((ObjectOverlay) o).click(p);
			break;
		}

		Overlay.removeOverlay(store);

	}

	private void InteractableCursorClick(MouseEvent e, Point p) {
		if (SwingUtilities.isLeftMouseButton(e)) {

			InteractableCursorOverlay.interactableOverlay.i
					.setX(InteractableCursorOverlay.interactableOverlay.i.getX() + GameCamera.activeCamera.x);

			Overlay.removeOverlay(InteractableCursorOverlay.interactableOverlay);

			LevelManager.activeLevel.addInteractable(InteractableCursorOverlay.interactableOverlay.i);

		} else if (SwingUtilities.isRightMouseButton(e)) {
			Overlay.removeOverlay(InteractableCursorOverlay.interactableOverlay);
		}
	}

	private void InteractableClick(Point p) {
		Overlay store = null;
		for (Overlay o : Overlay.getActiveOverlays()) {
			if (!(o instanceof InteractableOverlay)) {
				continue;
			}

			store = o;
			((InteractableOverlay) o).click(p);
			break;
		}

		Overlay.removeOverlay(store);

	}

	private void ObjectCursorClick(MouseEvent e, Point p) {
		if (SwingUtilities.isLeftMouseButton(e)) {

//			selection = ActiveSelection.MAIN;
			ObjectCursorOverlay.objectOverlay.o
					.setX(ObjectCursorOverlay.objectOverlay.o.getX() + GameCamera.activeCamera.x);
			Overlay.removeOverlay(ObjectCursorOverlay.objectOverlay);
			LevelManager.activeLevel.addObject(ObjectCursorOverlay.objectOverlay.o);
			Overlay.addOverlay(new ObjectCursorOverlay(
					ObjectList.getObject(ObjectList.getObjectEnum(ObjectCursorOverlay.objectOverlay.o))));

		} else if (SwingUtilities.isRightMouseButton(e)) {
			selection = ActiveSelection.MAIN;
			Overlay.removeOverlay(ObjectCursorOverlay.objectOverlay);
		}
	}

	private void leftClickFinalize(Point p) {
		Point temp = new Point(p.x + GameCamera.activeCamera.x,
				GameCamera.cameraHeight - (p.y + GameCamera.activeCamera.y));

		Sprite s = Sprite.getSprite(temp, LevelManager.activeLevel.getSprites());
		if (s != null) {
			s.setSelected(true);
			return;
		}

		GameObject o = GameObject.getObject(temp);
		if (o != null) {
			o.setSelected(true);
			return;
		}

		Interactable i = Interactable.getInteractable(temp, LevelManager.activeLevel.getInteractables());

		if (i != null) {
			i.setSelected(true);
			return;
		}

		Background b = Background.getBackground(temp, LevelManager.activeLevel.getBackgrounds());
		/*
		 * if (b != null && (b instanceof ColoredBackground)) { new
		 * BackgroundSettings((ColoredBackground) b).setVisible(true); return; }
		 */

		if (b != null) {
			b.setSelected(true);
			return;
		}

		Selectable.clearSelection();
	}

	@Override
	public void activate(JFrame f) {
		f.getContentPane().addMouseListener(this);
		f.getContentPane().addMouseMotionListener(this);

	}

	@Override
	public void disable(JFrame f) {
		f.getContentPane().removeMouseListener(this);
		f.getContentPane().removeMouseMotionListener(this);

	}

}
