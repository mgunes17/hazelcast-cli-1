package com.hazelcast.cli;

import static com.hazelcast.cli.CLI.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bilal on 08/07/16.
 */
public class ControlUtil {
	private static Logger logger = LoggerFactory.getLogger(ControlUtil.class);
	
    public static boolean checkCredentials() {
    	logger.trace("Check credentials");
        if (!settings.isConnectedToCluster) {
        	logger.trace("Credentials are not set");
            System.out.println("Credentials are not set. Using default ones");
            new CommandSetCredentials("dev", "dev-pass");
            System.out.println("You can change credentials by typing set-credentials [group-name] [password]");

        }
        return true;
    }
}
