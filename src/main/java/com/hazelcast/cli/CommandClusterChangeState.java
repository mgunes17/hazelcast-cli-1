package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class CommandClusterChangeState {
	private static Logger logger = LoggerFactory.getLogger(CommandClusterChangeState.class);
	
    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
        	logger.info("Credentials are false");
            return;
        }

        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String identityPath = properties.identityPath;
        String groupName = properties.clusterName;
        String password = properties.password;
        String clusterPort = properties.memberPort;
        OptionSpec changeState = com.hazelcast.cli.CommandOptions.changeClusterState;

        String stateParam = ((String) result.valueOf(changeState)).toLowerCase();

        if (!(stateParam.equals("active") || stateParam.equals("passive") || stateParam.equals("frozen"))) {
        	logger.info("ChangeState parameters are invalid");
            System.out.println("Invalid change state parameter. State parameter should be one of active, passive or frozen \n");
            return;
        }
        String changeClusterStateCmd = buildCommandChangeClusterState(hostIp, clusterPort, groupName, password, stateParam);

        logger.info("SshExecuter is running");
        SshExecutor.exec(user, hostIp, port, changeClusterStateCmd, false, identityPath, true);

    }

    private static String buildCommandChangeClusterState(String hostIp, String clusterPort, String groupName, String password, String stateParam) {
    	logger.trace("Build command change cluster state");
        //default: curl --data "dev&dev-pass&active" http://127.0.0.1:5701/hazelcast/rest/management/cluster/changeState
        return "curl --data \"" + groupName + "&" + password + "&" + stateParam + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/changeState";
    }

}
