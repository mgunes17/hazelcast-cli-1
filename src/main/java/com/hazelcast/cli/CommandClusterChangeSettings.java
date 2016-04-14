package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import java.util.Set;

public class CommandClusterChangeSettings {

    public static void apply(OptionSet result, ConsoleReader reader, Set<MachineSettings> machines, ClusterSettings properties) throws Exception {

        if (!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing create-cluster");
            return;
        }

        String memberName = reader.readLine("Enter the member tag that commands will be executed");
        if (!memberName.equals("")) {
            MachineSettings machineSettings = MachineSettings.getMachine(null, machines, CLI.members.get(memberName).getKey());
            properties.hostIp = machineSettings.hostIp;
            properties.user = machineSettings.userName;
            properties.port = machineSettings.sshPort;
            properties.identityPath = machineSettings.identityPath;
            properties.memberPort = CLI.members.get(memberName).getValue();
        }

    }

}
