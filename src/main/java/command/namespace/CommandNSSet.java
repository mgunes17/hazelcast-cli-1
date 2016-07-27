package command.namespace;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandNSSet {
	public static void apply(OptionSet result){
		CLI.nameSpace = (String) result.nonOptionArguments().get(0);
		
	}
}
