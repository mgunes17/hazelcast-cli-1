package com.hazelcast.cli;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * Created by mefeakengin on 1/19/16.
 */
public class CommandClusterGetState {


    public static void apply (OptionSet result, ClusterSettings properties) throws Exception {

        if(!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String identityPath = properties.identityPath;

        OptionSpec optionGroupName = com.hazelcast.cli.CommandOptions.optionGroupName;
        OptionSpec optionPassword = com.hazelcast.cli.CommandOptions.optionPassword;
        OptionSpec optionClusterPort = com.hazelcast.cli.CommandOptions.optionClusterPort;

        String groupName;
        if (result.has(optionGroupName)) {
            groupName = (String) result.valueOf(optionGroupName);
        } else {
            groupName = "dev";
            System.out.println("Group name is not specified, default group name is set to: " + groupName);
        }

        String password;
        if (result.has(optionPassword)) {
            password = (String) result.valueOf(optionPassword);
        } else {
            password = "dev-pass";
            System.out.println("Group name is not specified, default password is set to: " + password);
        }

        String clusterPort;
        if (result.has(optionClusterPort)) {
            clusterPort = (String) result.valueOf(optionClusterPort);
        } else {
            clusterPort = "5701";
            System.out.println("Group name is not specified, default clusterPort is set to: " + clusterPort);
        }

        String killNodeCmd = buildCommandGetClusterState(hostIp, clusterPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, killNodeCmd, false, identityPath);

    }

    private static String buildCommandGetClusterState(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/state
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/state";
    }

}
