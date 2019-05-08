package com.booksaw.corruption.listeners;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.controls.ControlList;
import com.booksaw.corruption.controls.ControlsManager;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.trigger.Trigger;
import com.booksaw.corruption.render.overlays.EditorOverlay;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.selection.Selectable;

public class EditorKeyListener implements Listener, KeyListener {

	public boolean ctrl;

	@Override
	public void activate(JFrame f) {
		f.addKeyListener(this);
	}

	@Override
	public void disable(JFrame f) {
		f.removeKeyListener(this);

	}

	@Override
	public void keyPressed(KeyEvent e) {

//		System.out.println(e.getKeyCode());

		if (ControlsManager.isKeyUsed(ControlList.DELETE, e)) {
			Selectable.deleteSelected();
			LevelManager.activeLevel.getSaveManager().changes();
		} else if (ControlsManager.isKeyUsed(ControlList.CONTROL, e)) {
			ctrl = true;
		} else if (ControlsManager.isKeyUsed(ControlList.TESTMODE, e)) {
			((EditorController) Corruption.main.controller).toogleTestMode();
		} else if (ControlsManager.isKeyUsed(ControlList.HIDE, e)) {
			EditorOverlay.activeOverlay.toggle();
		} else if (ControlsManager.isKeyUsed(ControlList.TRIGGERS, e)) {
			Trigger.showTriggers = (Trigger.showTriggers) ? false : true;
		} else {
			switch (e.getKeyCode()) {
			case 67:
				if (ctrl) {
					// copy
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

					StringSelection s;

					String temp = "";
					for (Selectable tmp : Selectable.getSelectables()) {
						temp = temp + tmp.getCopy() + "\n";
					}

					s = new StringSelection(temp);
					clipboard.setContents(s, s);
					LevelManager.activeLevel.getSaveManager().changes();

				}
				break;
			case 86:
				if (ctrl) {
					try {
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						Transferable t = clipboard.getContents(null);
						if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
							String data = (String) t.getTransferData(DataFlavor.stringFlavor);

							String[] split = data.split("\n");
							Selectable.clearSelection();
							for (String s : split) {
								LevelManager.activeLevel.runLine(s, true);
							}

						}
					} catch (Exception ex) {

					}
					LevelManager.activeLevel.getSaveManager().changes();
				}
				break;
			case 83:
				if (ctrl)
					LevelManager.activeLevel.getSaveManager().save();
				break;
			case 90:
				if (ctrl) {
					LevelManager.activeLevel.getSaveManager().undo();
				}
				break;
			case 89:
				if (ctrl) {
					LevelManager.activeLevel.getSaveManager().redo();
				}
				break;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (ControlsManager.isKeyUsed(ControlList.CONTROL, e)) {
			ctrl = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
