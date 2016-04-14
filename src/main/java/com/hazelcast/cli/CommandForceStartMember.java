package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;


public class CommandForceStartMember {

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

        if (!result.has(CommandOptions.forceStart)) {
            System.out.println("Please specify a nodename to force-start");
            return;
        }

        String nodeName = (String) result.valueOf(CommandOptions.forceStart);
        String hostName = CLI.members.get(nodeName).getKey();
        String memberForceStartPort = CLI.members.get(nodeName).getValue();
        MachineSettings machineSettings = MachineSettings.getMachine(null, machines, hostName);
        String user = machineSettings.userName;
        String hostIp = machineSettings.hostIp;
        String groupName = properties.clusterName;
        String password = properties.password;
        String identityPath = machineSettings.identityPath;
        int port = machineSettings.sshPort;


        String forceStartCmd = buildCommandForceStartMember(hostIp, memberForceStartPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, forceStartCmd, false, identityPath, false);

    }

    public static String buildCommandForceStartMember(String hostIp, String clusterPort, String groupName, String password) {
        return "curl --data \"" + groupName + "&" + password + "&" + hostIp + "&" + clusterPort + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/forceStart";
    }

}
