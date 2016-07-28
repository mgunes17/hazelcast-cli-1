package command.namespace;

import com.hazelcast.cli.CLI;

public class CommandNSReset {
	
	public static void apply() throws Exception{
		CLI.nameSpace = null;
	}
}
