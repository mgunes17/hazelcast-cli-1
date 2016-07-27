package com.hazelcast.cli;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class CommandClusterChangeState {

    public static void apply(OptionSet result, ClusterSettings properties) {

        if (!ControlUtil.checkCredentials()) {
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
            System.out.println("Invalid change state parameter. State parameter should be one of active, passive or frozen \n");
            return;
        }
        String changeClusterStateCmd = buildCommandChangeClusterState(hostIp, clusterPort, groupName, password, stateParam);

        try {
			SshExecutor.exec(user, hostIp, port, changeClusterStateCmd, false, identityPath, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    private static String buildCommandChangeClusterState(String hostIp, String clusterPort, String groupName, String password, String stateParam) {
        //default: curl --data "dev&dev-pass&active" http://127.0.0.1:5701/hazelcast/rest/management/cluster/changeState
        return "curl --data \"" + groupName + "&" + password + "&" + stateParam + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/changeState";
    }

}
