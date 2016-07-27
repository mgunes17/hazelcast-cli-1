package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import static com.hazelcast.cli.CLI.settings;

public class CommandSetCredentials {

    CommandSetCredentials(String groupName, String password) {
        settings.clusterName = groupName;
        settings.password = password;
        System.out.println("Credentials are successfully set.[ " + settings.clusterName + "/" + settings.password + " ]");
        settings.isConnectedToCluster = true;
    }

    CommandSetCredentials() {
    }

    public static void apply(OptionSet result, ConsoleReader reader) throws Exception {
        if (result.nonOptionArguments().size() < 2) {
            System.out.println("Please specify group name and password");
            System.out.println("Usage: set-credentials [group-name] [password]");
            System.out.println("Type help to see command options.");
            return;
        }
        settings.clusterName = (String) result.nonOptionArguments().get(0);
        settings.password = (String) result.nonOptionArguments().get(1);
        System.out.println("Credentials are successfully set.[ " + settings.clusterName + "/" + settings.password + " ]");
        settings.isConnectedToCluster = true;
    }

}
