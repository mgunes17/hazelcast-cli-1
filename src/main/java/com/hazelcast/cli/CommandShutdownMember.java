package com.hazelcast.cli;

import com.jcraft.jsch.JSchException;
import com.pastdev.jsch.scp.ScpFile;
import joptsimple.OptionSet;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static com.hazelcast.cli.CLI.files;
import static com.hazelcast.cli.CLI.members;
import static com.hazelcast.cli.CLI.sessions;


public class CommandShutdownMember {

    public static void apply(OptionSet result, Set<HostSettings> machines, ClusterSettings properties) {

        if (!ControlUtil.checkCredentials()) {
            return;
        }

        if (!result.has(CommandOptions.killMember)) {
            System.out.println("Please specify a member tag");
            System.out.println("Usage: kill-member [TAG]");
            System.out.println("Type --help to see command options.");
            return;
        }

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
        try {
			System.out.println(SshExecutor.exec(user, hostIp, port, killNodeCmd, false, identityPath, false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File file = files.get(hostName);
        ArrayList<String> list = new ArrayList<String>();
        try {
			for (String str : FileUtils.readLines(file)) {
			    if (!str.equals(memberKillPort + " " + nodeName)) list.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			FileUtils.writeLines(file, list, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ScpFile scpFile = new ScpFile(sessions.get(hostName),
                "/home", "ubuntu", "hazelcast", "members.txt");
        try {
			scpFile.copyFrom(file);
		} catch (IOException | JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        members.remove(nodeName);
        files.put(hostName, file);

    }

    public static String buildCommandKillMember(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/killNode
        return "curl --data \"" + groupName + "&" + password + "\" http://127.0.0.1" + ":" + clusterPort + "/hazelcast/rest/management/cluster/memberShutdown";
    }

}
