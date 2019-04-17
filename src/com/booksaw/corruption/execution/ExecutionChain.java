package com.booksaw.corruption.execution;

import java.text.DecimalFormat;
import java.util.List;

import com.booksaw.corruption.configuration.YamlConfiguration;

public class ExecutionChain {

	String baseRef;
	int count = 0;
	YamlConfiguration config;

	public ExecutionChain(String baseRef, YamlConfiguration config) {

		this.baseRef = baseRef;
		this.config = config;

		advance();
	}

	public void advance() {
		count++;

		String countStr = new DecimalFormat("00").format(count);

		List<String> nextSet = config.getStringList(baseRef + ".a" + countStr);

		if (nextSet.size() == 0) {
			// end of the chain
			return;
		}

		new ExecutionSet(this, nextSet);

	}

}
