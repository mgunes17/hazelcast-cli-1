package com.hazelcast.cli;

import joptsimple.OptionSet;

/**
 * Created by mefeakengin on 1/19/16.
 */
public class CommandManagementCenterStart {

    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if(!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String remotePath = properties.remotePath;
        String identityPath = properties.identityPath;

        //TODO: Allow .xml configuration!
        SshExecutor.exec(user, hostIp, port, buildCommandStartMC(remotePath, hostIp), false, identityPath);

    }

    public static String buildCommandStartMC(String path, String hostIp) {
        return "java -jar " + path + "/hazelcast/mancenter.war &&" + "echo " + "Management Center is established at " + hostIp + ":8080/mancenter &";
    }
}