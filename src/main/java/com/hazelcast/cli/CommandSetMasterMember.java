package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.hazelcast.cli.CLI.firstMember;

public class CommandSetMasterMember {
	private static Logger logger = LoggerFactory.getLogger(CommandSetMasterMember.class);
	
    public static void apply(OptionSet result, ConsoleReader reader, Set<HostSettings> machines, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
        	logger.trace("credentials are false");
            return;
        }
        if (result.nonOptionArguments().size() < 1) {
        	logger.trace("arguments are missing");
            System.out.println("Please specify member tag");
            System.out.println("Usage: set-master-member [TAG]");
            System.out.println("Type help to see command options.");
            return;
        }
        String memberName = (String) result.nonOptionArguments().get(0);
        if (CLI.members.get(memberName) == null) {
        	logger.trace("Member name is invalid");
            System.out.println("Please enter valid member tag");
            return;
        }
        HostSettings hostSettings = HostSettings.getMachine(null, machines, CLI.members.get(memberName).getKey());
        properties.tag = memberName;
        properties.hostIp = hostSettings.hostIp;
        properties.user = hostSettings.userName;
        properties.port = hostSettings.sshPort;
        properties.identityPath = hostSettings.identityPath;
        properties.memberPort = CLI.members.get(memberName).getValue();
        firstMember.put(properties.clusterName, memberName);
        logger.trace("Master member configured successfully");
        System.out.println("Master member configured successfully");
    }


}
