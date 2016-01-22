package com.hazelcast.cli;

import jline.console.ConsoleReader;

/**
 * Created by mefeakengin on 1/20/16.
 */
public class CommandClusterConnect {

    public static ClusterSettings apply(ConsoleReader reader) throws Exception {
        ClusterSettings settings = ClusterSettings.connectToCluster(reader);
        return settings;
    }

}
