package command.collection.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;

public class CommandMemorySet {
	private static Logger logger = LoggerFactory.getLogger(CommandMemorySet.class);
	
	public static void apply(OptionSet result) throws Exception {
		logger.trace("Directing collection filter for set");
		CommandMemoryAll.collectionFilter(result, "set");
	}
}
