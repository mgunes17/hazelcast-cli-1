package com.hazelcast.cli;

import static com.hazelcast.cli.CLI.settings;

/**
 * Created by bilal on 08/07/16.
 */
public class ControlUtil {
    public static boolean checkCredentials() {
        if (!settings.isConnectedToCluster) {
            System.out.println("Credentials are not set. Using default ones");
            new CommandSetCredentials("dev", "dev-pass");
            System.out.println("You can change credentials by typing set-credentials [group-name] [password]");

        }
        return true;
    }
}
