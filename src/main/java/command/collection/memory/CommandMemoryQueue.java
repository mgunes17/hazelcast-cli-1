package command.collection.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;

public class CommandMemoryQueue {
	private static Logger logger = LoggerFactory.getLogger(CommandMemoryQueue.class);
	
	public static void apply(OptionSet result) throws Exception {
		logger.trace("Directing collection filter for queue");
		CommandMemoryAll.collectionFilter(result, "queue");
	}
}
