package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterSettings {

	private static Logger logger = LoggerFactory.getLogger(ClusterSettings.class);
    public boolean isConnectedToCluster;

    //properties used for management
    public String tag;
    public String user;
    public String hostIp;
    public int port;
    public String remotePath;
    public String identityPath;

    //settings for the cluster
    public String clusterName = "dev";
    public String password = "dev-pass";
    public String memberPort;

    public ClusterSettings() {
    	logger.trace("ClusterSettings object is initializing");
        this.port = 22;
        this.isConnectedToCluster = false;
    }
}
