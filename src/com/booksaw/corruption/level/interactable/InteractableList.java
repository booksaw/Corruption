package com.booksaw.corruption.level.interactable;

public enum InteractableList {

	TABLE("table");

	public static Interactable getInteractable(InteractableList i) {

		return new Interactable(i.toString());

	}

	String ref;

	private InteractableList(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return ref;
	}

}
