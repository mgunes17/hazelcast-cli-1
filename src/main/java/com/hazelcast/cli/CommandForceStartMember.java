package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandForceStartMember {
	private static Logger logger = LoggerFactory.getLogger(CommandForceStartMember.class);
	
    public static void apply(OptionSet result, Set<HostSettings> machines, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
        	logger.trace("Credentials are false");
            return;
        }
        if (!result.has(CommandOptions.forceStart)) {
        	logger.trace("Not specified a member tag to force-start");
            System.out.println("Please specify a member tag to force-start");
            return;
        }


        logger.info("Reading info from properties");
        String user = properties.user;
        String hostIp = properties.hostIp;
        String groupName = properties.clusterName;
        String password = properties.password;
        String identityPath = properties.identityPath;
        String memberForceStartPort = properties.memberPort;
        int port = properties.port;


        String forceStartCmd = buildCommandForceStartMember(hostIp, memberForceStartPort, groupName, password);

        logger.info("Ssh is ecxecuting");
        SshExecutor.exec(user, hostIp, port, forceStartCmd, false, identityPath, true);

    }

    public static String buildCommandForceStartMember(String hostIp, String clusterPort, String groupName, String password) {
    	logger.trace("Build command force start member");
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/forceStart";
    }

}
