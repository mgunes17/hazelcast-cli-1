package com.hazelcast.cli;

import joptsimple.OptionSet;


public class CommandClusterListMember {

    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if (!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing create-cluster");
            return;
        }


        String user = properties.user;
        String hostIp = properties.hostIp;
        String clusterPort = properties.memberPort;
        String groupName = properties.clusterName;
        String password = properties.password;
        int port = properties.port;
        String identityPath = properties.identityPath;

        String listNodesCmd = buildCommandListNodes(hostIp, clusterPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, listNodesCmd, false, identityPath,true);

    }

    private static String buildCommandListNodes(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/nodes
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/nodes";
    }

}
