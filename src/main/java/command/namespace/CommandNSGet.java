package command.namespace;

import com.hazelcast.cli.CLI;

public class CommandNSGet {
	
	public static void apply() throws Exception {
		System.out.println(CLI.nameSpace);
	}
}
