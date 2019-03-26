package com.booksaw.corruption.level.interactable;

import com.booksaw.corruption.sprites.Sprite;

public enum Interaction {

	KILL("kill", new InteractionOption() {

		@Override
		public boolean run(String[] args, Sprite s, Interactable i) {
			s.reset(true);
			return true;
		}

	}), SPAWN("spawn", new InteractionOption() {

		@Override
		public boolean run(String[] args, Sprite s, Interactable i) {
			s.reset(false);
			return true;
		}

	}), TP("tp", new InteractionOption() {

		@Override
		public boolean run(String[] args, Sprite s, Interactable i) {

			s.setX(Integer.parseInt(args[1]));
			s.setY(Integer.parseInt(args[2]));

			return false;
		}

	}), RELATIVE("relativetp", new InteractionOption() {

		@Override
		public boolean run(String[] args, Sprite s, Interactable i) {
			s.setX(i.getX() + (Double.parseDouble(args[1]) * i.getWidth()));
			s.setY(i.getY() + (Double.parseDouble(args[2]) * i.getHeight()));
			return false;
		}

	});

	public static boolean execute(String info, Sprite s, Interactable i) {
		String[] args = info.split(":");

		Interaction in = getInteaction(args[0]);

		if (in == null) {
			return true;
		}

		return in.i.run(args, s, i);

	}

	public static Interaction getInteaction(String s) {

		for (Interaction i : Interaction.values()) {
			if (i.ref.equals(s)) {
				return i;
			}
		}

		return null;

	}

	InteractionOption i;
	String ref;

	private Interaction(String ref, InteractionOption i) {
		this.i = i;
		this.ref = ref;
	}

}
