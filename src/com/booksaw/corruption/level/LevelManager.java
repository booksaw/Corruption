package com.booksaw.corruption.level;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.Renderable;
import com.booksaw.corruption.Updatable;
import com.booksaw.corruption.audioEngine.AudioInstance;
import com.booksaw.corruption.audioEngine.AudioPlayer;
import com.booksaw.corruption.level.background.Background;
import com.booksaw.corruption.level.background.ColoredBackground;
import com.booksaw.corruption.level.interactable.Interactable;
import com.booksaw.corruption.level.interactable.InteractableComponent;
import com.booksaw.corruption.level.meta.BackgroundColorMeta;
import com.booksaw.corruption.level.meta.CameraLocationMeta;
import com.booksaw.corruption.level.meta.LevelDimensionsMeta;
import com.booksaw.corruption.level.meta.Meta;
import com.booksaw.corruption.level.objects.Block;
import com.booksaw.corruption.level.objects.Door;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.level.objects.Slime;
import com.booksaw.corruption.level.objects.Spike;
import com.booksaw.corruption.level.save.SaveManager;
import com.booksaw.corruption.level.trigger.Trigger;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.render.overlays.menu.LevelCompleteOverlay;
import com.booksaw.corruption.render.overlays.menu.PauseOverlay;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.GameController;
import com.booksaw.corruption.selection.Selectable;
import com.booksaw.corruption.sprites.BlueNPC;
import com.booksaw.corruption.sprites.Guard;
import com.booksaw.corruption.sprites.Player;
import com.booksaw.corruption.sprites.Sprite;

/**
 * Used to manage a level from file loading to execution
 * 
 * @author James
 *
 */
public class LevelManager {

	// all objects in the level (keeping track for rendering and collision
	List<GameObject> levelObjects = new ArrayList<>();

	// all objects in the level (keeping track for rendering and collision
	List<Sprite> sprites = new ArrayList<>();

	List<Background> backgrounds = new ArrayList<>();

	List<Interactable> interactables = new ArrayList<>();
	List<InteractableComponent> components = new ArrayList<>();

	List<Renderable> toRender = new ArrayList<>();
	List<Updatable> updatable = new ArrayList<>();
	List<Trigger> triggers = new ArrayList<>();

	public int fails = 0;
	public int time = 0;
	private boolean trackTime = true;
	private long startTime;

	private SaveManager saveManager;

	// the file the level has been loaded from
	private File f;
	// all meta data so it can be executed
	public List<Meta> metaData = new ArrayList<>();
	// the dimensions of the level
	public Dimension levelDimensions;

	private boolean changed = false;

	/**
	 * 
	 * @return the level dimensions
	 */
	public Dimension getLevelDimensions() {
		return levelDimensions;
	}

	/**
	 * Used so the active level can be accessed
	 */
	public static LevelManager activeLevel;

	// background colour
	public Color backgroundColor = Color.WHITE;

	/**
	 * Contstructor to load the level information from file
	 * 
	 * @param level
	 */
	public LevelManager(File level) {
		activeLevel = this;
		f = level;
		load();
	}

	public void load() {

		resetLists();

		saveManager = new SaveManager(this);

	}

	/**
	 * Used to set the level to becomre the default level
	 */
	public void resetToDefault() {
		resetToDefault(true);
	}

	/**
	 * Used to set the level to become the default level
	 * 
	 * @param save
	 */
	private void resetToDefault(boolean save) {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
			br = new BufferedReader(new FileReader(Config.ASSETSPATH + File.separator + "default.level"));

			String line = "";

			while ((line = br.readLine()) != null) {
				runLine(line);
				if (save) {
					pw.println(line);
				}
			}

			pw.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run just before the level is displayed
	 */
	public void finalise() {
		activeLevel = this;
		sortRenderable();
		// activating all meta
		for (Meta m : metaData) {
			m.execute();
		}

		startTime = System.currentTimeMillis();

	}

	/**
	 * Anything which is required to do on the disable
	 */
	public void disableLevel() {

	}

	public void runLine(String line) {
		runLine(line, false);
	}

	/**
	 * Manages setting what each line should be done
	 * 
	 * @param line
	 */
	public void runLine(String line, boolean select) {
		// splitting the line into its components
		String[] split = line.split(":");
		// setting which type of component it is
		switch (split[0]) {
		case "meta":
			makeMeta(split[1], split[2]);
			break;
		case "object":
			makeObject(split[1], split[2], select);
			break;
		case "background":
			makeBackground(split[1], split[2], select);
			break;
		case "sprite":
			makeSprite(split[1], split[2], select);
			break;
		case "interactable":
			makeInteractable(split[1], select);
			break;
		case "trigger":
			makeTrigger(split[1]);
		}

	}

	/**
	 * Used to setup meta from the file
	 * 
	 * @param type what type of meta it is
	 * @param info info to be parsed into the meta class
	 */
	private void makeMeta(String type, String info) {
		// finds what type of meta it is and setup the appropriate meta
		switch (type) {
		case "leveldimensions":
			metaData.add(new LevelDimensionsMeta(info));
			break;
		case "cameralocation":
			metaData.add(new CameraLocationMeta(info));
			break;
		case "backgroundcolor":
			metaData.add(new BackgroundColorMeta(info));
		}

	}

	private void makeInteractable(String info, boolean select) {
		addInteractable(new Interactable(info, select, this));
	}

	/**
	 * Used to setup any objects
	 * 
	 * @param type what type of object it is
	 * @param info info to be parsed into the object class
	 */
	private void makeObject(String type, String info, boolean select) {

		switch (type) {
		case "block":
			Block b = new Block(info);
			b.setSelected(select);
			addObject(b);
			break;
		case "door":
			Door d = new Door(info);
			d.setSelected(select);
			addObject(d);
			break;
		case "spike":
			Spike s = new Spike(info);
			s.setSelected(select);
			addObject(s);
			break;
		case "slime":
			Slime sl = new Slime(info);
			sl.setSelected(select);
			addObject(sl);
			break;
		}

	}

	private void makeBackground(String type, String info, boolean select) {
		switch (type) {
		case "colored":
			ColoredBackground cb = new ColoredBackground(info);
			cb.setSelected(select);
			addBackground(cb);
			break;
		}

	}

	private void makeSprite(String type, String info, boolean select) {
		switch (type) {
		case "player":
			Player p = new Player(info);
			p.setSelected(select);
			addSprite(p);
			break;
		case "bluenpc":
			BlueNPC b = new BlueNPC(info);
			b.setSelected(select);
			addSprite(b);
			break;
		case "guard":
			Guard g = new Guard(info);
			g.setSelected(select);
			addSprite(g);
			break;

		}

	}

	private void makeTrigger(String info) {
		Trigger t = new Trigger(info);
		addTrigger(t);

	}

	public List<Meta> getMetaData() {
		return metaData;
	}

	public List<GameObject> getLevelObjects() {
		return levelObjects;
	}

	public List<Background> getBackgrounds() {
		return backgrounds;
	}

	public List<Sprite> getSprites() {
		return sprites;
	}

	public List<Interactable> getInteractables() {
		return interactables;
	}

	public List<Trigger> getTriggers() {
		return triggers;
	}

	public Sprite getActivePlayer() {
		for (Sprite s : sprites) {
			if (s.activePlayer) {
				return s;
			}
		}
		return null;
	}

	public void addSprite(Sprite s) {
		sprites.add(s);
		toRender.add(s);
		sortRenderable();

	}

	public void addObject(GameObject o) {
		levelObjects.add(o);
		toRender.add(o);
		sortRenderable();
	}

	public void addBackground(Background b) {
		backgrounds.add(b);
		toRender.add(b);
		sortRenderable();
	}

	public void addInteractable(Interactable i) {
		interactables.add(i);
//		toRender.add(i);
//		sortRenderable();
	}

	public void addInteractableComponent(InteractableComponent i) {
		components.add(i);
		toRender.add(i);
		sortRenderable();
	}

	public void addTrigger(Trigger t) {
		triggers.add(t);
		toRender.add(t);
	}

	public void removeObject(GameObject o) {
		levelObjects.remove(o);
		toRender.remove(o);
	}

	public void removeBackground(Background b) {
		backgrounds.remove(b);
		toRender.remove(b);
	}

	public void removeSprite(Sprite s) {
		sprites.remove(s);
		toRender.remove(s);
	}

	public void removeInteractable(Interactable i) {
		interactables.remove(i);
//		toRender.remove(i);
	}

	public void removeInteractableComponent(InteractableComponent i) {
		components.remove(i);
		toRender.remove(i);
	}

	public void removeTrigger(Trigger t) {
		triggers.remove(t);
		toRender.remove(t);
	}

	public void setActive() {
		activeLevel = this;
	}

	private List<Updatable> toRemove = new ArrayList<>();
	private List<Updatable> toAdd = new ArrayList<>();

	boolean active = true;

	public void update(int time) {

		if (!active || PauseOverlay.paused) {
			return;
		}

		for (Sprite s : sprites) {
			if (s.controllable || s.needsUpdating()) {
				s.update(time);
			}
		}

		for (Updatable u : updatable) {
			u.update(time);
		}

		for (Updatable u : toRemove) {
			updatable.remove(u);
		}

		toRemove = new ArrayList<>();

		for (Updatable u : toAdd) {
			updatable.add(u);
		}
		toAdd = new ArrayList<>();

		if (trackTime)
			this.time = (int) ((System.currentTimeMillis() - startTime) / 1000);

	}

	public List<Updatable> getToRemove() {
		return toRemove;
	}

	public List<Updatable> getToAdd() {
		return toAdd;
	}

	/**
	 * Used to remove all items from a level
	 */
	public void erase() {

		resetLists();
		// resets the file to the default file
		resetToDefault(false);
	}

	/**
	 * Used to reset all the lists of stored items
	 */
	public void resetLists() {
		// resets all stored items
		metaData = new ArrayList<>();
		sprites = new ArrayList<>();
		levelObjects = new ArrayList<>();
		backgrounds = new ArrayList<>();
		interactables = new ArrayList<>();
		toRender = new ArrayList<>();
		components = new ArrayList<>();
		updatable = new ArrayList<>();
		triggers = new ArrayList<>();
		Guard.searching = false;
		Selectable.resetAllSelectables();
	}

	public void resetAllSprites() {
		for (Sprite s : sprites) {
			s.reset();
		}
	}

	/**
	 * Used for a fail (if the player dies etc.)
	 */
	public void reset() {
		for (Sprite s : sprites) {
			if (s.controllable || s instanceof Guard) {
				s.reset();

			}
		}

		for (Trigger t : triggers) {
			t.reset();
		}
		AudioPlayer.playSound(AudioInstance.DEATH);
		fails++;
	}

	public void clearActivePlayer() {
		for (Sprite s : sprites) {
			if (s.activePlayer) {
				s.setActiveplayer(false);
			}
		}
	}

	public File getF() {
		return f;
	}

	public List<Renderable> getToRender() {
		return toRender;
	}

	public void sortRenderable() {
		Renderable[] renders = toRender.toArray(new Renderable[toRender.size()]);

		Renderable temp;

		for (int i = 0; i < renders.length; i++) {
			for (int j = 1; j < renders.length - i; j++) {
				if (renders[j - 1].getPriority() < renders[j].getPriority()) {
					temp = renders[j - 1];
					renders[j - 1] = renders[j];
					renders[j] = temp;
					setChanged(true);
				}
			}
		}

		toRender = new ArrayList<>(Arrays.asList(renders));
	}

	public void finish() {
		if (Corruption.main.controller instanceof EditorController) {
			reset();
			((EditorController) Corruption.main.controller).toogleTestMode();
			return;
		}
		// setting the next level as the active level
		new GameController(saveManager.getNextLevel());

		// stopping all updates
		active = false;

		// Setting the level complete overlay
		Overlay.addOverlay(new LevelCompleteOverlay());

	}

	public void stopTime() {
		trackTime = false;
	}

	public void startTime() {
		this.startTime = (int) ((System.currentTimeMillis() - (time * 1000)) / 1000);
		this.time = (int) ((System.currentTimeMillis() - startTime) / 1000);
		trackTime = true;
	}

	public List<Updatable> getUpdatable() {
		return updatable;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public SaveManager getSaveManager() {
		return saveManager;
	}

	public boolean hasActiveLevel() {
		System.out.println(saveManager.getNextLevel());
		return !saveManager.getNextLevel().equals("");
	}

}
