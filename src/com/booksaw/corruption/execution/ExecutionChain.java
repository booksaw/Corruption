package com.booksaw.corruption.execution;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.booksaw.corruption.configuration.YamlConfiguration;

public class ExecutionChain {

	String baseRef;
	int count = 0;
	YamlConfiguration config;

	public ExecutionChain(String baseRef, YamlConfiguration config, boolean execute) {

		this.baseRef = baseRef;
		this.config = config;

		if (execute)
			advance();
	}

	boolean active = true;

	public void advance() {
		if (!active) {
			return;
		}
		count++;

		String countStr = new DecimalFormat("00").format(count);

		List<String> nextSet = config.getStringList(baseRef + ".a" + countStr);

		if (nextSet.size() == 0) {
			// end of the chain

			return;
		}

		ExecutionSet set = new ExecutionSet(this, nextSet);
		set.run();
	}

	/**
	 * Used to read the config for the editor
	 * 
	 * @return
	 */
	public HashMap<String, ExecutionSet> getSets() {
		HashMap<String, ExecutionSet> sets = new HashMap<>();
		int count = 0;

		while (true) {
			count++;

			String countStr = new DecimalFormat("00").format(count);

			List<String> nextSet = config.getStringList(baseRef + ".a" + countStr);

			if (nextSet.size() == 0) {
				// end of the chain
				return sets;
			}
			sets.put(baseRef + ".a" + countStr, new ExecutionSet(this, nextSet));
		}

	}

	public String getNextReference(int countt) {
		int count = 0;

		while (true) {
			count++;

			String countStr = new DecimalFormat("00").format(count);

			List<String> nextSet = config.getStringList(baseRef + ".a" + countStr);

			if (nextSet.size() == 0) {
				// end of the chain

				countStr = new DecimalFormat("00").format(count + countt);
				return baseRef + ".a" + countStr;
			}
		}

	}

	public void reset() {
		active = false;
	}

}
