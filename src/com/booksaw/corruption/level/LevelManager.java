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
import java.util.List;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.level.background.Background;
import com.booksaw.corruption.level.background.ColoredBackground;
import com.booksaw.corruption.level.meta.BackgroundColorMeta;
import com.booksaw.corruption.level.meta.CameraLocationMeta;
import com.booksaw.corruption.level.meta.LevelDimensionsMeta;
import com.booksaw.corruption.level.meta.Meta;
import com.booksaw.corruption.level.meta.PlayerSpawnMeta;
import com.booksaw.corruption.level.objects.Block;
import com.booksaw.corruption.level.objects.Door;
import com.booksaw.corruption.level.objects.GameObject;
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

	// the file the level has been loaded from
	File f;
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
		// storing the file
		f = level;
		// setting up the file reader
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(level));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		boolean read = false;
		// line to store read liens to
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				read = true;
				runLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!read) {

			PrintWriter pw = null;
			try {
				pw = new PrintWriter(level);
				br = new BufferedReader(new FileReader(Config.ASSETSPATH + File.separator + "default.level"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				while ((line = br.readLine()) != null) {
					read = true;
					runLine(line);
					pw.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			pw.close();

		}

		try {
			br.close();
		} catch (Exception e) {
		}

	}

	/**
	 * Run just before the level is displayed
	 */
	public void finalise() {
		activeLevel = this;
		// activating all meta
		for (Meta m : metaData) {
			m.execute();
		}

	}

	/**
	 * Anything which is required to do on the disable
	 */
	public void disableLevel() {

	}

	/**
	 * Manages setting what each line should be done
	 * 
	 * @param line
	 */
	public void runLine(String line) {
		// splitting the line into its components
		String[] split = line.split(":");
		// setting which type of component it is
		switch (split[0]) {
		case "meta":
			makeMeta(split[1], split[2]);
			break;
		case "object":
			makeObject(split[1], split[2]);
			break;
		case "background":
			makeBackground(split[1], split[2]);
		case "sprite":
			makeSprite(split[1], split[2]);
		}

	}

	/**
	 * Used to setup meta from the fil
	 * 
	 * @param type what type of meta it is
	 * @param info info to be parsed into the meta class
	 */
	private void makeMeta(String type, String info) {
		// finds what type of meta it is and setup the appropriate meta
		switch (type) {
		case "playerspawn":
			metaData.add(new PlayerSpawnMeta(info));
			break;
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

	/**
	 * Used to setup any objects
	 * 
	 * @param type what type of object it is
	 * @param info info to be parsed into the object class
	 */
	private void makeObject(String type, String info) {

		switch (type) {
		case "block":
			levelObjects.add(new Block(info));
			break;
		case "door":
			levelObjects.add(new Door(info));
			break;
		}

	}

	private void makeBackground(String type, String info) {
		switch (type) {
		case "colored":
			backgrounds.add(new ColoredBackground(info));
			break;
		}

	}

	private void makeSprite(String type, String info) {
		switch (type) {
		case "player":
			addSprite(new Player(info));
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

	public void addSprite(Sprite s) {
		sprites.add(s);

	}

	public void addObject(GameObject o) {
		levelObjects.add(o);
	}

	public void addBackground(Background b) {
		backgrounds.add(b);
	}

	public void removeObject(GameObject o) {
		levelObjects.remove(o);
	}

	public void removeBackground(Background b) {
		backgrounds.remove(b);
	}

	public void removeSprites(Sprite s) {
		sprites.remove(s);
	}

	public void save() {

		try {
			PrintWriter pw = new PrintWriter(f);

			for (Meta m : metaData) {
				String s = m.toString();
				if (!s.equals(""))
					pw.println(m);
			}

			for (GameObject o : levelObjects) {
				if (o.needsSaving()) {
					String s = o.toString();
					if (!s.equals(""))
						pw.println(o);
				}
			}

			for (Background b : backgrounds) {
				String s = b.toString();
				if (!s.equals(""))
					pw.println(b);
			}

			for (Sprite s : sprites) {
				String st = s.toString();
				if (!st.equals(""))
					pw.println(s);
			}

			pw.close();
			changed = false;
		} catch (Exception e) {
		}
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

}
