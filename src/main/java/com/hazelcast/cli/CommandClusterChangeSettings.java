package com.hazelcast.cli;

import joptsimple.OptionSet;

/**
 * Created by mefeakengin on 1/20/16.
 */
public class CommandClusterChangeSettings {

    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if(!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

        //TODO: Apply this!
        System.out.println("This actually changes the cluster settings ... ");

    }

}
