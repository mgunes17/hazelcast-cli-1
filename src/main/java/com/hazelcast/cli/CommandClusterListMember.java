package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joptsimple.OptionSet;


public class CommandClusterListMember {
	private static Logger logger = LoggerFactory.getLogger(CommandClusterListMember.class);
	
    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
        	logger.trace("Credentials is false");
            return;
        }
        
        logger.info("Cluster settings is reading");
        String user = properties.user;
        String hostIp = properties.hostIp;
        String clusterPort = properties.memberPort;
        String groupName = properties.clusterName;
        String password = properties.password;
        int port = properties.port;
        String identityPath = properties.identityPath;

        String listNodesCmd = buildCommandListNodes(hostIp, clusterPort, groupName, password);

        System.out.println(SshExecutor.exec(user, hostIp, port, listNodesCmd, false, identityPath, false).replace(",", System.lineSeparator()));

    }

    private static String buildCommandListNodes(String hostIp, String clusterPort, String groupName, String password) {
    	logger.trace("Build command list nodes");
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/nodes
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/nodes";
    }
}
