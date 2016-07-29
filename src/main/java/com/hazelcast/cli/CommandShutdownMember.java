package com.hazelcast.cli;

import com.pastdev.jsch.scp.ScpFile;
import joptsimple.OptionSet;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import static com.hazelcast.cli.CLI.files;
import static com.hazelcast.cli.CLI.members;
import static com.hazelcast.cli.CLI.sessions;


public class CommandShutdownMember {
	private static Logger logger = LoggerFactory.getLogger(CommandShutdownMember.class);
	
    public static void apply(OptionSet result, Set<HostSettings> machines, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
        	logger.trace("Credentials are false");
            return;
        }

        if (!result.has(CommandOptions.killMember)) {
        	logger.trace("Argument is missing");
            System.out.println("Please specify a member tag");
            System.out.println("Usage: kill-member [TAG]");
            System.out.println("Type --help to see command options.");
            return;
        }

        logger.trace("Readingi info");
        String nodeName = (String) result.valueOf(CommandOptions.killMember);
        String hostName = CLI.members.get(nodeName).getKey();
        String memberKillPort = CLI.members.get(nodeName).getValue();
        HostSettings hostSettings = HostSettings.getMachine(null, machines, hostName);
        String user = hostSettings.userName;
        String hostIp = hostSettings.hostIp;
        String groupName = properties.clusterName;
        String password = properties.password;
        String identityPath = hostSettings.identityPath;
        int port = hostSettings.sshPort;


        String killNodeCmd = buildCommandKillMember(hostIp, memberKillPort, groupName, password);
        System.out.println(SshExecutor.exec(user, hostIp, port, killNodeCmd, false, identityPath, false));
        File file = files.get(hostName);
        ArrayList<String> list = new ArrayList<String>();
        for (String str : FileUtils.readLines(file)) {
        	logger.trace("Reading file");
            if (!str.equals(memberKillPort + " " + nodeName)) list.add(str);
        }
        FileUtils.writeLines(file, list, false);
        ScpFile scpFile = new ScpFile(sessions.get(hostName),
                "/home", "ubuntu", "hazelcast", "members.txt");
        scpFile.copyFrom(file);
        logger.trace("Member has been removed");
        members.remove(nodeName);
        files.put(hostName, file);

    }

    public static String buildCommandKillMember(String hostIp, String clusterPort, String groupName, String password) {
    	logger.trace("Build command kill member");
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/killNode
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/memberShutdown";
    }

}
