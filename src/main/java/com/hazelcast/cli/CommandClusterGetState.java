package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;

public class CommandClusterGetState {
	private static Logger logger = LoggerFactory.getLogger(CommandClusterGetState.class);
	
    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
        	logger.trace("Credentials are false");
            return;
        }

        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String identityPath = properties.identityPath;
        String groupName = properties.clusterName;
        String password = properties.password;
        String clusterPort = properties.memberPort;
        String clusterStateCmd = buildCommandGetClusterState(hostIp, clusterPort, groupName, password);

        logger.info("Ssh is executing");
        SshExecutor.exec(user, hostIp, port, clusterStateCmd, false, identityPath, true);

    }

    private static String buildCommandGetClusterState(String hostIp, String clusterPort, String groupName, String password) {
    	logger.trace("Build command get cluster state");
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/state";
    }

}
