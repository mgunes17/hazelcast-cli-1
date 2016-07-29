package command.namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;
	
public class CommandNSReset {
	private static Logger logger = LoggerFactory.getLogger(CommandNSReset.class);
	
	public static void apply() throws Exception{
		logger.trace("Resetting the namespace");
		System.out.println("Namespace is null");
		CLI.nameSpace = null;
	}
}
