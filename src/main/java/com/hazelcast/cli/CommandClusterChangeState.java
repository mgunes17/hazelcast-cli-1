package com.hazelcast.cli;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class CommandClusterChangeState {

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
        OptionSpec changeState = com.hazelcast.cli.CommandOptions.changeClusterState;

        String stateParam = ((String) result.valueOf(changeState)).toLowerCase();

        if (!(stateParam.equals("active") || stateParam.equals("passive") || stateParam.equals("frozen"))) {
            System.out.println("Invalid change state parameter. State parameter should be one of active, passive or frozen \n");
        }

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

        String changeClusterStateCmd = buildCommandChangeClusterState(hostIp, clusterPort, groupName, password, stateParam);

        SshExecutor.exec(user, hostIp, port, changeClusterStateCmd, false, identityPath, false);

    }

    private static String buildCommandChangeClusterState(String hostIp, String clusterPort, String groupName, String password, String stateParam) {
        //default: curl --data "dev&dev-pass&active" http://127.0.0.1:5701/hazelcast/rest/management/cluster/changeState
        return "curl --data \"" + groupName + "&" + password + "&" + stateParam + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/changeState";
    }

}
