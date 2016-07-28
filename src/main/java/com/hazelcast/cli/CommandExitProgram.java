package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExitProgram {
	private static Logger logger = LoggerFactory.getLogger(CommandExitProgram.class);
	
    public static void apply() throws Exception {

        //TODO: Improve the exit!
        System.out.println("Exiting the program ... ");
        logger.info("Hazelcast instance is shutting");
        CLI.instance.shutdown();
        System.exit(-1);

    }
}