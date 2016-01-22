package com.hazelcast.cli;

import jline.console.ConsoleReader;

/**
 * Created by mefeakengin on 1/20/16.
 */
public class CommandClusterDisconnect {

    public static ClusterSettings apply() throws Exception {
        System.out.println("Disconnected from the cluster.");
        ClusterSettings settings = new ClusterSettings();
        return settings;
    }
}
