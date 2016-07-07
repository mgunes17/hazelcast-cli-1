package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import static com.hazelcast.cli.CLI.settings;

public class CommandClusterConnect {

    public static void apply(OptionSet result, ConsoleReader reader) throws Exception {
        if (result.nonOptionArguments().size() < 2) {
            System.out.println("Please specify group name and password");
            System.out.println("Usage: set-credentials [group-name] [password]");
            System.out.println("Type --help to see command options.");
            return;
        }
        settings.clusterName = (String) result.nonOptionArguments().get(0);
        settings.password = (String) result.nonOptionArguments().get(1);
        System.out.println("Credentials for connecting to a cluster set.");
        settings.isConnectedToCluster = true;
    }

}
