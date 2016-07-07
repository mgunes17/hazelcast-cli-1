package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;


public class CommandClusterKillMember {

    public static void apply(OptionSet result, Set<HostSettings> machines, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
            return;
        }

        if (!result.has(CommandOptions.killMember)) {
            System.out.println("Please specify a member tag");
            System.out.println("Usage: kill-member [TAG]");
            System.out.println("Type --help to see command options.");
            return;
        }

        String nodeName = (String) result.valueOf(CommandOptions.killMember);
        String hostName = CLI.members.get(nodeName).getKey();
        String memberKillPort = CLI.members.get(nodeName).getValue();
        HostSettings hostSettings = HostSettings.getMachine(null, machines, hostName);
        String user = hostSettings.userName;
        String hostIp = hostSettings.hostIp;
        String groupName = properties.clusterName;
        String password = properties.password;
        String identityPath = hostSettings.identityPath;
        int port = hostSettings.sshPort;


        String killNodeCmd = buildCommandKillMember(hostIp, memberKillPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, killNodeCmd, false, identityPath, false);

    }

    public static String buildCommandKillMember(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/killNode
        return "curl --data \"" + groupName + "&" + password + "&" + hostIp + "&" + clusterPort + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/killNode";
    }

}
