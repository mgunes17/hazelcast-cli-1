package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;


public class CommandForceStartMember {

    public static void apply(OptionSet result, Set<HostSettings> machines, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
            return;
        }
        if (!result.has(CommandOptions.forceStart)) {
            System.out.println("Please specify a member tag to force-start");
            return;
        }


        String user = properties.user;
        String hostIp = properties.hostIp;
        String groupName = properties.clusterName;
        String password = properties.password;
        String identityPath = properties.identityPath;
        String memberForceStartPort = properties.memberPort;
        int port = properties.port;


        String forceStartCmd = buildCommandForceStartMember(hostIp, memberForceStartPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, forceStartCmd, false, identityPath, true);

    }

    public static String buildCommandForceStartMember(String hostIp, String clusterPort, String groupName, String password) {
        return "curl --data \"" + groupName + "&" + password + "&" + hostIp + "&" + clusterPort + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/forceStart";
    }

}
