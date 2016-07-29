package command.collection.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;

public class CommandMemoryMap {
	private static Logger logger = LoggerFactory.getLogger(CommandMemoryMap.class);
	
	public static void apply(OptionSet result) throws Exception {
		logger.trace("Directing collection filter for map");
		CommandMemoryAll.collectionFilter(result, "map");
	}
}
