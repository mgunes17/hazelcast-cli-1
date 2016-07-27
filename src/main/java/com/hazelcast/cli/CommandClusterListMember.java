package com.hazelcast.cli;

import joptsimple.OptionSet;


public class CommandClusterListMember {

    public static void apply(OptionSet result, ClusterSettings properties) {

        if (!ControlUtil.checkCredentials()) {
            return;
        }
        String user = properties.user;
        String hostIp = properties.hostIp;
        String clusterPort = properties.memberPort;
        String groupName = properties.clusterName;
        String password = properties.password;
        int port = properties.port;
        String identityPath = properties.identityPath;

        String listNodesCmd = buildCommandListNodes(hostIp, clusterPort, groupName, password);

        try {
			System.out.println(SshExecutor.exec(user, hostIp, port, listNodesCmd, false, identityPath, false).replace(",", System.lineSeparator()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    private static String buildCommandListNodes(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/nodes
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/nodes";
    }

}
