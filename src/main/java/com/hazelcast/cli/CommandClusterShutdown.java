package com.hazelcast.cli;

import com.jcraft.jsch.JSchException;
import com.pastdev.jsch.scp.ScpFile;
import joptsimple.OptionSet;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.hazelcast.cli.CLI.files;
import static com.hazelcast.cli.CLI.hosts;
import static com.hazelcast.cli.CLI.sessions;

public class CommandClusterShutdown {

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

        String shutdownCmd = buildCommandShutdownCluster(hostIp, clusterPort, groupName, password);

        try {
			SshExecutor.exec(user, hostIp, port, shutdownCmd, false, identityPath, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        CLI.firstMember.remove(properties.clusterName);

        for (HostSettings host : hosts) {
            File file = files.get(host.hostName);
            try {
				FileUtils.writeStringToFile(file, "", false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            files.put(host.hostName, file);
            ScpFile scpFile = new ScpFile(sessions.get(host.hostName),
                    "/home", "ubuntu", "hazelcast", "members.txt");
            try {
				scpFile.copyFrom(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public static String buildCommandShutdownCluster(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/shutdown
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/clusterShutdown";
    }

}
