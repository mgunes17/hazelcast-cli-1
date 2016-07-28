package command.collection.memory;

import joptsimple.OptionSet;

public class CommandMemoryQueue {

	public static void apply(OptionSet result) throws Exception {
		CommandMemoryAll.collectionFilter(result, "queue");
	}
}
