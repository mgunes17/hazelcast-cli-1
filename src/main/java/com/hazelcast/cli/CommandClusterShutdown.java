package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;

/**
 * Created by mefeakengin on 1/19/16.
 */
public class CommandClusterShutdown {

    public static void apply(OptionSet result, Set<MachineSettings> machines, ClusterSettings properties) throws Exception {

        if (!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }
        String machineName = CLI.firstMember.get(CLI.currentCluster);
        MachineSettings machineSettings = MachineSettings.getMachine(null, machines, machineName);
        String user = machineSettings.userName;
        String hostIp = machineSettings.hostIp;
        int port = properties.port;
        String identityPath = machineSettings.identityPath;

        String groupName = CLI.currentCluster;
        String password = CLI.currentClusterPassword;
        String clusterPort = "5701";

        String shutdownCmd = buildCommandShutdownCluster(hostIp, clusterPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, shutdownCmd, false, identityPath);

    }

    public static String buildCommandShutdownCluster(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/shutdown
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/shutdown";
    }

}
