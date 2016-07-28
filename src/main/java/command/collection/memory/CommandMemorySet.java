package command.collection.memory;

import joptsimple.OptionSet;

public class CommandMemorySet {
	
	public static void apply(OptionSet result) throws Exception {
		CommandMemoryAll.collectionFilter(result, "set");
	}
}
