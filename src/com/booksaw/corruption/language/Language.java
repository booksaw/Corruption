package com.booksaw.corruption.language;

import java.util.HashMap;

/**
 * Used for all messages for language processing
 * 
 * @author James
 *
 */
public class Language {

	/**
	 * List of all the messages (loaded in ram)
	 */
	private static HashMap<String, String> messages = new HashMap<>();

	/**
	 * TODO make from file Used to load the messages
	 */
	public static void loadLanguage() {
		messages.put("title", "Corruption");

		messages.put("menu.load", "Load game");
		messages.put("menu.new", "Start game");
		messages.put("menu.editor", "Editor");
		messages.put("editor.cancel", "Cancel");
		messages.put("editor.ok", "OK");
		messages.put("editor.delete", "Delete");
		messages.put("editor.tool.cancel", "Press this to cancel any changes");
		messages.put("editor.tool.ok", "Press this to save any changes");
		messages.put("editor.tool.delete", "Press this to delete the block");
		messages.put("editor.color", "Press to select the color");
		messages.put("editor.color.title", "Color picker");
		messages.put("editor.trash", "Do you want to clear the level?");

		messages.put("pause.resume", "Resume");
		messages.put("pause.quit", "Quit");
		messages.put("pause.savequit", "Save and Quit");
		messages.put("pause.save", "Do you want to save?");

		messages.put("cursor.block", "Block");
		messages.put("cursor.background", "Background");
		messages.put("cursor.selection", "Select");
		messages.put("cursor.trigger", "Trigger");

	}

	/**
	 * returns the correct message for the reference
	 * 
	 * @param ref
	 * @return
	 */
	public static String getMessage(String ref) {
		return messages.get(ref);
	}

}
