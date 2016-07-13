package com.hazelcast.cli;

import jline.console.ConsoleReader;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class CommandAddHost {

    public static void apply(ConsoleReader reader, Set<HostSettings> hosts, String hostName) throws Exception {
        if (hostName == null) hostName = "";
        boolean hostNameExisting = false;
        while (hostName.equals("") || hostNameExisting) {
            hostName = reader.readLine("Please give a unique name to your remote host: ");
            hostNameExisting = false;
            for (HostSettings host : hosts) {
                if (hostName.equals(host.hostName)) {
                    hostNameExisting = true;
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
//                userName = reader.readLine("User name to access to the remote host: ");
//                if (userName.equals("")) {
//                    System.out.println("Please enter a valid user name");
//                }
//            }
//
//            String hostIp = "";
//            while (hostIp.equals("")) {
//                hostIp = reader.readLine("IP to access to the remote host: ");
//                if (hostIp.equals("")) {
//                    System.out.println("Please enter a valid ip address");
//                }
//            }
//
//            String identityPath = "";
//            while (identityPath.equals("")) {
//                identityPath = reader.readLine("Identity path of the local access key for accessing the remote host:  ");
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
        String userName = prop.getProperty(hostName + ".user");
        String hostIp = prop.getProperty(hostName + ".ip");
        String remotePath = prop.getProperty(hostName + ".remotePath");
        String identityPath = prop.getProperty(hostName + ".identityPath");


        HostSettings host = new HostSettings(hostName, userName, hostIp, remotePath, identityPath);

        System.out.println("Connection settings set for " + host.userName + "@" + host.hostIp);
        String message = SshExecutor.exec(host.userName, host.hostIp, 22, "", false, host.identityPath, false);
        if ((message == null) || (!message.equals("exception"))) {
            System.out.println("Host " + host.hostName + " is added.");
            hosts.add(host);
        } else {
            System.out.println("Could not connect to the host.");
            System.out.println("Please try to add a host again.");
        }
        return;
    }

}



