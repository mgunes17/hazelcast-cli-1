package com.hazelcast.cli;

import static com.hazelcast.cli.CLI.settings;

/**
 * Created by bilal on 08/07/16.
 */
public class ControlUtil {
    public static boolean checkCredentials() {
        if (!settings.isConnectedToCluster) {
            System.out.println("Please first set credentials by typing set-credentials [group-name] [password]");
            return false;
        }
        return true;
    }
}
