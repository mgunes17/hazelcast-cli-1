package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandClusterDisconnect {
	private static Logger logger = LoggerFactory.getLogger(CommandClusterDisconnect.class);
	
    public static ClusterSettings apply() throws Exception {
    	logger.trace("Disconnected from the cluster");
        System.out.println("Disconnected from the cluster.");
        ClusterSettings settings = new ClusterSettings();
        return settings;
    }
}
