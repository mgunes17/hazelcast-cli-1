package com.hazelcast.cli;


public class CommandClusterDisconnect {

    public static ClusterSettings apply() {
        System.out.println("Disconnected from the cluster.");
        ClusterSettings settings = new ClusterSettings();
        return settings;
    }
}
