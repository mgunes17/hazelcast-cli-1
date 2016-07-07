package com.hazelcast.cli;

import jline.console.ConsoleReader;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class CommandAddMachine {

    public static void apply(ConsoleReader reader, Set<HostSettings> machines, String hostName) throws Exception {
        try {
            String machineName = "";
            if (hostName != null) {
                machineName = hostName;
            }
            boolean machineNameExisting = false;
            while (machineName.equals("") || machineNameExisting) {
                machineName = reader.readLine("Please give a unique name to your remote machine: ");
                machineNameExisting = false;
                for (HostSettings machine : machines) {
                    if (machineName.equals(machine.hostName)) {
                        machineNameExisting = true;
                    }
                }
//                if (hostName.equals("")) {
//                    System.out.println("Please enter a valid machine name");
//                } else if (machineNameExisting) {
//                    System.out.println("The machine name is already in use");
//                }
//            }
//
//            String userName = "";
//            while (userName.equals("")) {
//                userName = reader.readLine("User name to access to the remote machine: ");
//                if (userName.equals("")) {
//                    System.out.println("Please enter a valid user name");
//                }
//            }
//
//            String hostIp = "";
//            while (hostIp.equals("")) {
//                hostIp = reader.readLine("IP to access to the remote machine: ");
//                if (hostIp.equals("")) {
//                    System.out.println("Please enter a valid ip address");
//                }
//            }
//
//            String identityPath = "";
//            while (identityPath.equals("")) {
//                identityPath = reader.readLine("Identity path of the local access key for accessing the remote machine:  ");
//                if (identityPath.equals("")) {
//                    System.out.println("Please enter a valid identity path");
//                }
//            }
//
//            String remotePath = "";
//            while (remotePath.equals("")) {
//                remotePath = reader.readLine("Hazelcast path of the remote machine: ");
//                if (remotePath.equals("")) {
//                    System.out.println("Please enter a valid remote path.");
//                }
//            }
            }
            Properties prop = new Properties();
            InputStream is = CLI.class.getClassLoader().getResourceAsStream("cli.properties");
            prop.load(is);
            String userName = prop.getProperty(machineName + ".user");
            String hostIp = prop.getProperty(machineName + ".ip");
            String remotePath = prop.getProperty(machineName + ".remotePath");
            String identityPath = prop.getProperty(machineName + ".identityPath");


            HostSettings machine = new HostSettings(machineName, userName, hostIp, remotePath, identityPath);

            System.out.println("Connection settings set for " + machine.userName + "@" + machine.hostIp);
            String message = SshExecutor.exec(machine.userName, machine.hostIp, 22, "", false, machine.identityPath, false);
            if ((message == null) || (!message.equals("exception"))) {
                System.out.println("Machine " + machine.hostName + " is added.");
                machines.add(machine);
            } else {
                System.out.println("Could not connect to the machine.");
                System.out.println("Please try to add a machine again.");
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please try to add a machine again.");
            return;
        }
    }

}



