package com.hazelcast.cli;

import jline.console.ConsoleReader;

import java.util.Set;

public class CommandAddMachine {

    public static void apply(ConsoleReader reader, Set<MachineSettings> machines) throws Exception {
        try {
            String machineName = "";
            while (machineName.equals("")) {
                machineName = reader.readLine("Please give a unique name to your remote machine: ");
                boolean machineNameExisting = false;
                for (MachineSettings machine : machines) {
                    if (machineName.equals(machine.machineName)) {
                        machineNameExisting = true;
                    }
                }
                if (machineName.equals("") || machineNameExisting) {
                    System.out.println("Please enter a valid machine name");
                }
            }

            String userName = "";
            while (userName.equals("")) {
                userName = reader.readLine("User name to access to the remote machine: ");
                if (userName.equals("")) {
                    System.out.println("Please enter a valid user name");
                }
            }

            String hostIp = "";
            while (hostIp.equals("")) {
                hostIp = reader.readLine("IP to access to the remote machine: ");
                if (hostIp.equals("")) {
                    System.out.println("Please enter a valid ip address");
                }
            }

            String identityPath = "";
            while (identityPath.equals("")) {
                identityPath = reader.readLine("Identity path of the local access key for accessing the remote machine:  ");
                if (identityPath.equals("")) {
                    System.out.println("Please enter a valid identity path");
                }
            }

            String remotePath = "";
            while (remotePath.equals("")) {
                remotePath = reader.readLine("Hazelcast path of the remote machine: ");
                if (remotePath.equals("")) {
                    System.out.println("Please enter a valid remote path.");
                }
            }

            MachineSettings machine = new MachineSettings(machineName, userName, hostIp, remotePath, identityPath);

            System.out.println("Connection settings set for " + machine.userName + "@" + machine.hostIp);
            String message = SshExecutor.exec(machine.userName, machine.hostIp, 22, "", false, machine.identityPath);
            if ((message == null) || (!message.equals("exception"))) {
                System.out.println("Machine " + machine.machineName + " is added.");
                machines.add(machine);
            } else {
                System.out.println("Could not connect to the machine.");
                System.out.println("Please try to add a machine again.");
            }
            return;
        } catch (Exception e) {
            System.out.println("Please try to add a machine again.");
            return;
        }
    }

}



