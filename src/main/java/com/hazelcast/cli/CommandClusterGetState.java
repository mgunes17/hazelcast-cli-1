package com.hazelcast.cli;

import joptsimple.OptionSet;

public class CommandClusterGetState {


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
        String clusterStateCmd = buildCommandGetClusterState(hostIp, clusterPort, groupName, password);

        try {
			SshExecutor.exec(user, hostIp, port, clusterStateCmd, false, identityPath, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    private static String buildCommandGetClusterState(String hostIp, String clusterPort, String groupName, String password) {
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/state";
    }

}
