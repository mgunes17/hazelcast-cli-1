package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

/**
 * Created by mefeakengin on 1/20/16.
 */
public class CommandClusterConnect {

    public static ClusterSettings apply(OptionSet result, ConsoleReader reader) throws Exception {
        ClusterSettings settings = ClusterSettings.connectToCluster(result,reader);
        return settings;
    }

}
