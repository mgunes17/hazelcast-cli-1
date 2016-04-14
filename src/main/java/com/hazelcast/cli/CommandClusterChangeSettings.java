package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import java.util.Set;

public class CommandClusterChangeSettings {

    public static void apply(OptionSet result, ConsoleReader reader, Set<MachineSettings> machines, ClusterSettings properties) throws Exception {

        if (!properties.isConnectedToCluster) {
            System.out.println("Please first connect to a cluster by typing --cluster-connect.");
            return;
        }

        String hostname = reader.readLine("Enter the machine name that commands will be executed");
        if (!hostname.equals("")) {
            MachineSettings machineSettings = MachineSettings.getMachine(null, machines, hostname);
            properties.hostIp = machineSettings.hostIp;
            properties.user = machineSettings.userName;
            properties.port = machineSettings.sshPort;
            properties.identityPath = machineSettings.identityPath;
        }
        String port = reader.readLine("Enter the port name that commands will be executed");
        if (!port.equals("")) {
            properties.memberPort = port;
        }
    }

}
