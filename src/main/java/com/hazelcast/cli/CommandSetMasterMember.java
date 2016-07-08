package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import java.util.Set;

import static com.hazelcast.cli.CLI.firstMember;

public class CommandSetMasterMember {

    public static void apply(OptionSet result, ConsoleReader reader, Set<HostSettings> machines, ClusterSettings properties) throws Exception {

        if (!ControlUtil.checkCredentials()) {
            return;
        }
        if (result.nonOptionArguments().size() < 1) {
            System.out.println("Please specify member tag");
            System.out.println("Usage: set-master-member [TAG]");
            System.out.println("Type help to see command options.");
            return;
        }
        String memberName = (String) result.nonOptionArguments().get(0);
        if (CLI.members.get(memberName) == null) {
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
        System.out.println("Master member configured successfully");
    }


}
