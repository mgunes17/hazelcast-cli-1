package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;


public class CommandManagementCenterStart {
	private static Logger logger = LoggerFactory.getLogger(CommandManagementCenterStart.class);
	
    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if(!properties.isConnectedToCluster) {
        	logger.trace("Not connected to cluster");
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

        logger.info("Reading host info");
        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String remotePath = properties.remotePath;
        String identityPath = properties.identityPath;

        //TODO: Allow .xml configuration!
        logger.info("Ssh is executing");
        SshExecutor.exec(user, hostIp, port, buildCommandStartMC(remotePath, hostIp), false, identityPath, false);

    }

    public static String buildCommandStartMC(String path, String hostIp) {
    	logger.trace("Build command start mc");
        return "java -jar " + path + "/hazelcast/mancenter.war &&" + "echo " + "Management Center is established at " + hostIp + ":8080/mancenter &";
    }
}