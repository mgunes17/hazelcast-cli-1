package command.collection.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;

public class CommandMemoryList {
	private static Logger logger = LoggerFactory.getLogger(CommandMemoryList.class);
	
	public static void apply(OptionSet result) throws Exception {
		logger.trace("Directing collection filter for list");
		CommandMemoryAll.collectionFilter(result, "list");
	}
}
