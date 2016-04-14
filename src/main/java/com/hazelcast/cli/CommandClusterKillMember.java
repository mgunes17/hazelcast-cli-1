package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;


public class CommandClusterKillMember {

    public static void apply(OptionSet result, Set<MachineSettings> machines, ClusterSettings properties) throws Exception {

        if (!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

//        String user = properties.user;
//        String hostIp = properties.hostIp;
//        String clusterPort = properties.memberPort;
//        String groupName = properties.clusterName;
//        String password = properties.password;
//        int port = properties.port;
//        String identityPath = properties.identityPath;

        if (!result.has(CommandOptions.killMember)) {
            System.out.println("Please specify a host to kill");
            return;
        }
        if (!result.has(CommandOptions.optionClusterPort)) {
            System.out.println("Please specify a port to kill");
            return;
        }

        String hostName = (String) result.valueOf(CommandOptions.killMember);
        String memberKillPort = (String) result.valueOf(CommandOptions.optionClusterPort);
        MachineSettings machineSettings = MachineSettings.getMachine(null, machines, hostName);
        String user = machineSettings.userName;
        String hostIp = machineSettings.hostIp;
        String groupName = properties.clusterName;
        String password = properties.password;
        String identityPath = machineSettings.identityPath;
        int port = machineSettings.sshPort;


        String killNodeCmd = buildCommandKillMember(hostIp, memberKillPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, killNodeCmd, false, identityPath);

    }

    public static String buildCommandKillMember(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/killNode
        return "curl --data \"" + groupName + "&" + password + "&" + hostIp + "&" + clusterPort + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/killNode";
    }

}
