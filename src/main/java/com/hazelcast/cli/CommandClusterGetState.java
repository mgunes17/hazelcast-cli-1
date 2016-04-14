package com.hazelcast.cli;

import joptsimple.OptionSet;

public class CommandClusterGetState {


    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if (!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String identityPath = properties.identityPath;
        String groupName = properties.clusterName;
        String password = properties.password;
        String clusterPort = properties.memberPort;
        String clusterStateCmd = buildCommandGetClusterState(hostIp, clusterPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, clusterStateCmd, false, identityPath, true);

    }

    private static String buildCommandGetClusterState(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/state
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/state";
    }

}
