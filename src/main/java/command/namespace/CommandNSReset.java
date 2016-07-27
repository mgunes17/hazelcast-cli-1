package command.namespace;

import com.hazelcast.cli.CLI;

public class CommandNSReset {
	public static void apply(){
		CLI.nameSpace = null;
	}
}
