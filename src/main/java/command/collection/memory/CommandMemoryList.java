package command.collection.memory;

import joptsimple.OptionSet;

public class CommandMemoryList {

	public static void apply(OptionSet result) throws Exception {
		CommandMemoryAll.collectionFilter(result, "list");
	}
}
