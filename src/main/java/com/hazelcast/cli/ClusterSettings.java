package com.hazelcast.cli;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class ClusterSettings {

    public boolean isConnectedToCluster;
    public boolean isConnectedToMachine;
    public String localPath;

    //properties used for management
    public String user;
    public String hostIp;
    public int port;
    public String remotePath;
    public String identityPath;

    //settings for the cluster
    public String clusterName;
    public String password;
    public String memberPort;

    public ClusterSettings() {

        this.port = 22;
        this.localPath = System.getProperty("user.home");
        this.isConnectedToCluster = false;
        this.isConnectedToMachine = false;

    }

    public static ClusterSettings connectToMachine(ConsoleReader reader, OptionSet result) throws Exception {

//        ClusterSettings settings = new ClusterSettings();
//        String isRemote = "";
//        while (!(isRemote.equals("remote")||isRemote.equals("local"))) {
//            isRemote = reader.readLine(
//                    "Will Hazelcast be installed in localhost or remote host?\n" +
//                            "Please type \"remote or local\" ");
//            if (isRemote.equals("remote")) {
//                setRemoteSettings(reader, settings);
//            } else if (isRemote.equals("local")) {
//                setLocalSettings(reader, settings);
//            }
//        }
        ClusterSettings settings = ClusterSettings.setMachineSettings(result);
        return settings;
    }

    public static ClusterSettings connectToCluster(ConsoleReader reader) throws Exception {
        ClusterSettings settings = new ClusterSettings();
        //TODO: Do we want cluster name? How so?
//        settings.clusterName = "";
//        while (!settings.clusterName.equals("")) {
//            settings.user = reader.readLine("Cluster name: ");
//            if (settings.user.equals("")) {
//                System.out.println("Please enter a valid cluster name");
//            }
//        }
        setRemoteSettings(reader, settings);
        settings.isConnectedToCluster = true;
        return settings;
    }

    private static ClusterSettings setMachineSettings(OptionSet result) throws Exception {
        ClusterSettings settings = new ClusterSettings();
        try {
            if (result.has("user-name") && result.has("ip") && result.has("remote-path") && result.has("identity-path")) {
                settings.user = (String) result.valueOf("user-name");
                settings.hostIp = (String) result.valueOf("ip");
                settings.remotePath = (String) result.valueOf("remote-path");
                settings.identityPath = (String) result.valueOf("identity-path");
                System.out.println("Connection settings set for " + settings.user + "@" + settings.hostIp);
                String message = SshExecutor.exec(settings.user, settings.hostIp, 22, "", false, settings.identityPath);
                if ((message == null) || (!message.equals("exception"))) {
                    System.out.println("Connected to the machine.");
                    settings.isConnectedToMachine = true;
                } else {
                    System.out.println("Could not connect to the machine.");
                }
            } else {
                System.out.println("Please make sure to specify user-name, ip, remote-path and identity-path.");
            }
            return settings;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please try to connect again.");
            return settings;
        }
    }

    private static void setLocalSettings(ConsoleReader reader, ClusterSettings settings) throws Exception {
        System.out.println("Please set settings for local host.");
        settings.localPath = "";
        while (settings.localPath.equals("")) {
            settings.localPath = reader.readLine("Local path for installing Hazelcast: ");
            if (settings.user.equals("")) {
                System.out.println("Please enter a local path");
            }
        }
    }

    private static void setRemoteSettings(ConsoleReader reader, ClusterSettings settings) throws Exception {
//        settings.localPath = System.getProperty("user.home");
//        System.out.println("Please set settings for remote host.");
//        settings.user = "";
//        while (settings.user.equals("")) {
//            settings.user = reader.readLine("Remote user name: ");
//            if (settings.user.equals("")) {
//                System.out.println("Please enter a valid user name");
//            }
//        }

        settings.user = reader.readLine("User name of the remote machine: ");
        settings.identityPath = reader.readLine("Identity path to connect to remote machine: ");

        settings.clusterName = "";
        settings.clusterName = reader.readLine("Cluster name/group name: ");
        if (settings.clusterName.equals("")) {
            settings.clusterName = "dev";
            System.out.println("The cluster name is set to the default \"dev\"");
        }

        settings.password = "";
        settings.password = reader.readLine("Password of the cluster: ", '*');
        if (settings.password.equals("")) {
            settings.password = "dev-pass";
            System.out.println("The password is set to the default \"dev-pass\"");
        }

        settings.hostIp = "";
        while (settings.hostIp.equals("")) {
            settings.hostIp = reader.readLine("IP of the member node: ");
            if (settings.hostIp.equals("")) {
                System.out.println("Please enter a valid ip address");
            }
        }
//        settings.remotePath = "";
//        while (settings.remotePath.equals("")) {
//            settings.remotePath = reader.readLine("Hazelcast path of remote host: ");
//            if (settings.remotePath.equals("")) {
//                System.out.println("Please enter a valid remote path.");
//            }
//        }
//        settings.identityPath = "";
//        while (settings.identityPath.equals("")) {
//            settings.identityPath = reader.readLine("Identity path to access the remote host: ");
//            if (settings.identityPath.equals("")) {
//                System.out.println("Please enter a valid remote path.");
//            }
//        }
        //TODO: Port number handling
        settings.memberPort = "";
        settings.memberPort = reader.readLine("Port of the member node: ");
        if (settings.memberPort.equals("")) {
            settings.memberPort = "5701";
            System.out.println("The member port is set to the default \"5701\"");
        }

        System.out.println("Properties for connecting to a cluster set.");
        //TODO: Make sure to actually connect to a cluster
        System.out.println("Actually connected to a cluster.");

//        properties.port = 0;
//        while (properties.port == 0) {
//            try {
//                properties.port = Integer.parseInt(reader.readLine("Port number to connect to the remote machine: "));
//            } catch (java.lang.NumberFormatException e){
//                System.out.println("Please enter a valid port value");
//            }
//        }
    }

}
