package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import static com.hazelcast.cli.CLI.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandSetCredentials {
	private static Logger logger = LoggerFactory.getLogger(CommandSetCredentials.class);
	
    CommandSetCredentials(String groupName, String password) {
    	logger.trace("Credentials are initialiing");
        settings.clusterName = groupName;
        settings.password = password;
        System.out.println("Credentials are successfully set.[ " + settings.clusterName + "/" + settings.password + " ]");
        settings.isConnectedToCluster = true;
    }

    CommandSetCredentials() {
    }

    public static void apply(OptionSet result, ConsoleReader reader) throws Exception {
        if (result.nonOptionArguments().size() < 2) {
        	logger.trace("Arguments are missing");
            System.out.println("Please specify group name and password");
            System.out.println("Usage: set-credentials [group-name] [password]");
            System.out.println("Type help to see command options.");
            return;
        }
        
        settings.clusterName = (String) result.nonOptionArguments().get(0);
        settings.password = (String) result.nonOptionArguments().get(1);
        logger.trace("Credentials are successfully set");
        System.out.println("Credentials are successfully set.[ " + settings.clusterName + "/" + settings.password + " ]");
        settings.isConnectedToCluster = true;
    }

}
