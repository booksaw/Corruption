package com.booksaw.corruption.level;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.Renderable;
import com.booksaw.corruption.Updatable;
import com.booksaw.corruption.configuration.YamlConfiguration;
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
import com.booksaw.corruption.level.objects.Spike;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.GameController;
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

	public int fails = 0;
	public int time = 0;
	private boolean trackTime = true;
	private long startTime;

	private YamlConfiguration config;
	private String nextLevel;

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
		f = level;
		load();
	}

	public void load() {
		// resets all stored items
		metaData = new ArrayList<>();
		sprites = new ArrayList<>();
		levelObjects = new ArrayList<>();
		backgrounds = new ArrayList<>();
		interactables = new ArrayList<>();
		toRender = new ArrayList<>();
		components = new ArrayList<>();
		updatable = new ArrayList<>();

		config = new YamlConfiguration(f);

		nextLevel = config.getString("nextLevel");

		// setting up the file reader
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(new FileReader(f));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		boolean read = false;
//		// line to store read liens to
//		String line = "";
//		try {
//			while ((line = br.readLine()) != null) {
//				read = true;
//				runLine(line);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		List<String> levelInfo = config.getStringList("level");

		if (levelInfo == null || levelInfo.size() == 0) {
			resetLevel();
			return;
		}

		for (String str : levelInfo) {
			runLine(str);
		}

//		try {
//			br.close();
//		} catch (Exception e) {
//		}

	}

	private void resetLevel() {
		resetLevel(true);
	}

	private void resetLevel(boolean save) {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
			br = new BufferedReader(new FileReader(Config.ASSETSPATH + File.separator + "default.level"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line = "";

		try {
			while ((line = br.readLine()) != null) {
				runLine(line);
				if (save) {
					pw.println(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			pw.close();
			br.close();
		} catch (IOException e) {
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
		}

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

	public void save() {

		List<String> level = new ArrayList<>();

		for (Meta m : metaData) {
			String s = m.toString();
			if (!s.equals(""))
				level.add(s);
		}

		for (GameObject o : levelObjects) {
			if (o.needsSaving()) {
				String s = o.toString();
				if (!s.equals(""))
					level.add(s);
			}
		}

		for (Background b : backgrounds) {
			String s = b.toString();
			if (!s.equals("") && b.needsSaving())
				level.add(s);
		}

		for (Sprite s : sprites) {
			String st = s.toString();
			if (!st.equals(""))
				level.add(st);
		}

		for (Interactable i : interactables) {
			String s = i.toString();
			if (!s.equals("")) {
				level.add(s);
			}
		}

		changed = false;
		config.set("level", level);
		config.set("nextLevel", nextLevel);

		config.saveConfiguration();

	}

	public boolean hasChanged() {
		return changed;
	}

	public void changes() {
		changed = true;
	}

	public void setActive() {
		activeLevel = this;
	}

	private List<Updatable> toRemove = new ArrayList<>();

	public void update(int time) {
		for (Sprite s : sprites) {
			if (s.controllable) {
				s.update(time);
			}
		}

		toRemove = new ArrayList<>();

		for (Updatable u : updatable) {
			u.update(time);
		}

		for (Updatable u : toRemove) {
			updatable.remove(u);
		}

		if (trackTime)
			this.time = (int) ((System.currentTimeMillis() - startTime) / 1000);

	}

	public List<Updatable> getToRemove() {
		return toRemove;
	}

	/**
	 * Used to remove all items from a level
	 */
	public void erase() {

		// resets all stored items
		metaData = new ArrayList<>();
		sprites = new ArrayList<>();
		levelObjects = new ArrayList<>();
		backgrounds = new ArrayList<>();
		interactables = new ArrayList<>();
		toRender = new ArrayList<>();
		components = new ArrayList<>();
		updatable = new ArrayList<>();

		// resets the file to the default file
		resetLevel(false);
	}

	public void resetAll() {
		for (Sprite s : sprites) {
			s.reset();
		}
	}

	public void reset() {
		for (Sprite s : sprites) {
			if (s.controllable) {
				s.reset();

			}
		}

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

		boolean changed = false;
		Renderable temp;

		for (int i = 0; i < renders.length && !changed; i++) {
			changed = false;
			for (int j = 1; j < renders.length - i; j++) {
				if (renders[j - 1].getPriority() < renders[j].getPriority()) {
					temp = renders[j - 1];
					renders[j - 1] = renders[j];
					renders[j] = temp;
					changed = true;
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

		GameController gc = new GameController(nextLevel);
		gc.show();
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

	public String getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}

}
