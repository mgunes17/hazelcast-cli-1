package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import java.util.Set;

public class CommandClusterChangeSettings {

    public static void apply(OptionSet result, ConsoleReader reader, Set<HostSettings> machines, ClusterSettings properties) throws Exception {

        if (!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing create-cluster");
            return;
        }

        String memberName = reader.readLine("Enter the member tag that commands will be executed");
        if (!memberName.equals("")) {
            HostSettings hostSettings = HostSettings.getMachine(null, machines, CLI.members.get(memberName).getKey());
            properties.hostIp = hostSettings.hostIp;
            properties.user = hostSettings.userName;
            properties.port = hostSettings.sshPort;
            properties.identityPath = hostSettings.identityPath;
            properties.memberPort = CLI.members.get(memberName).getValue();
        }

    }

}
