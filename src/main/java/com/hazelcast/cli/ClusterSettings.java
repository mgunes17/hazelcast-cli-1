package com.hazelcast.cli;

public class ClusterSettings {

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
        this.port = 22;
        this.isConnectedToCluster = false;
    }
}
