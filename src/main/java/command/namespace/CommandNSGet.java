package command.namespace;

import com.hazelcast.cli.CLI;

public class CommandNSGet {
	public static void apply(){
		System.out.println(CLI.nameSpace);
	}
}
