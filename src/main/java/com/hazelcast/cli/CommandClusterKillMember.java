package com.hazelcast.cli;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * Created by mefeakengin on 1/19/16.
 */
public class CommandClusterKillMember {

    public static void apply (OptionSet result, ClusterSettings properties) throws Exception {

        if(!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

        String user = properties.user;
        String hostIp = properties.hostIp;
        String clusterPort = properties.memberPort;
        String groupName = properties.clusterName;
        String password = properties.password;
        int port = properties.port;
        String identityPath = properties.identityPath;

        if (!result.has(CommandOptions.killMember)) {
            System.out.println("Please specify a member port to kill");
            return;
        }

        String memberKillPort = (String) result.valueOf(CommandOptions.killMember);

        String killNodeCmd = buildCommandKillMember(hostIp, memberKillPort, groupName, password);

        SshExecutor.exec(user, hostIp, port, killNodeCmd, false, identityPath);

    }

    public static String buildCommandKillMember(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/killNode
        return "curl --data \"" + groupName + "&" + password + "&" + hostIp + "&" + clusterPort + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/killNode";
    }

}
