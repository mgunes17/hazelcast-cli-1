package com.hazelcast.cli;

import com.pastdev.jsch.scp.ScpFile;
import joptsimple.OptionSet;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.hazelcast.cli.CLI.files;
import static com.hazelcast.cli.CLI.hosts;
import static com.hazelcast.cli.CLI.sessions;

public class CommandClusterShutdown {
	private static Logger logger = LoggerFactory.getLogger(CommandClusterShutdown.class);
	
    public static void apply(OptionSet result, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
        	logger.trace("Credentials are false");
            return;
        }

        logger.info("Reading host settings from properties");
        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String identityPath = properties.identityPath;

        String groupName = properties.clusterName;
        String password = properties.password;
        String clusterPort = properties.memberPort;

        String shutdownCmd = buildCommandShutdownCluster(hostIp, clusterPort, groupName, password);

        logger.info("Ssh is executing");
        SshExecutor.exec(user, hostIp, port, shutdownCmd, false, identityPath, true);
        CLI.firstMember.remove(properties.clusterName);

        for (HostSettings host : hosts) {
        	logger.trace("Writing host settings to file");
            File file = files.get(host.hostName);
            FileUtils.writeStringToFile(file, "", false);
            files.put(host.hostName, file);
            ScpFile scpFile = new ScpFile(sessions.get(host.hostName),
                    "/home", "ubuntu", "hazelcast", "members.txt");
            scpFile.copyFrom(file);
        }
    }

    public static String buildCommandShutdownCluster(String hostIp, String clusterPort, String groupName, String password) {
    	logger.trace("Build commands shut down cluster");
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/shutdown
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/clusterShutdown";
    }

}
