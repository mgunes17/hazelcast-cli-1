package command.collection.memory;

import org.github.jamm.MemoryMeter;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.DistributedObject;

import joptsimple.OptionSet;

public class CommandMemoryMap {
	
	public static void apply(OptionSet result) throws Exception {
		CommandMemoryAll.collectionFilter(result, "map");
	}
}
