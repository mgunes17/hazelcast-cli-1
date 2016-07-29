package command.namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

public class CommandNSGet {
	private static Logger logger = LoggerFactory.getLogger(CommandNSGet.class);
	
	public static void apply() throws Exception {
		logger.trace("Getting the namespace");
		System.out.println(CLI.nameSpace);
	}
}
