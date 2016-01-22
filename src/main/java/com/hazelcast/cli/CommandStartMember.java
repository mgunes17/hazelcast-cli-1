package com.hazelcast.cli;

import joptsimple.OptionSet;

public class CommandStartMember {

    public static void apply (OptionSet result, ClusterSettings properties) throws Exception {

        if(!properties.isConnectedToMachine) {
            System.out.println(
                "Please first connect to a machine by typing " +
                "--connect-machine --user <user> --ip <ip> --remote-path <absolute path for hazelcast> --identity-path <password path>.");
            return;
        }

        String user = properties.user;
        String hostIp = properties.hostIp;
        int port = properties.port;
        String remotePath = properties.remotePath;
        String identityPath = properties.identityPath;

        if (!result.has(com.hazelcast.cli.CommandOptions.optionConfigFile)) {
            System.out.println("A config file is required.");
            return;
        }

        String clusterName = (String) result.valueOf(com.hazelcast.cli.CommandOptions.optionClusterName);
        String nodeName = (String) result.valueOf(com.hazelcast.cli.CommandOptions.optionNodeName);
        String configFile = (String) result.valueOf(com.hazelcast.cli.CommandOptions.optionConfigFile);
        String cmd = upload(user, hostIp, nodeName, configFile, remotePath);
        System.out.println("Uploading config file at path " + configFile);

        Runtime.getRuntime().exec(cmd);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Upload completed.");
        System.out.println("Starting instance...");
        String startCmd = start(remotePath, nodeName);

        //TODO: can I assume that java is already installed to Amazon EC2
        String pid = SshExecutor.exec(user, hostIp, port, startCmd, true, identityPath);

        //            //TODO: Standardize "/log.out"
        //            int oldLogLength = returnValue.lineCounter;
        //            String copyLogCmd = commandsBuilder.copyLog(user, hostIp, remotePath, localPath);
        ////            SshExecutor.exec(user, hostIp, port, copyLogCmd, true, remotePath);
        //            System.out.println("Copy the log command: " + copyLogCmd);
        //            Runtime.getRuntime().exec(copyLogCmd);
        //
        //            String printLog = commandsBuilder.printLog(localPath, oldLogLength);
        //            System.out.println("Print the most current log: " + printLog);
        //            Runtime.getRuntime().exec(printLog);

        System.out.println("Instance started : " + pid);

    }

    private static String upload(String user, String hostName, String nodeName, String configFile, String path) {
        if(configFile == null){
            configFile = path + "hazelcast/bin/hazelcast.xml";
        }
        //scp bin/hazelcast.xml mefeakengin@localhost:~/hazelcast-istanbul.xml
        return "scp " + configFile + " " + user + "@" + hostName + ":" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml";
    }

    private static String start(String path, String nodeName) {
        //java -cp "lib/*" -Dhazelcast.config=hazelcast-istanbul.xml com.hazelcast.core.server.StartServer & echo $!
//        return  "touch " + path + "/hazelcast/bin/log-" + nodeName + ".null && " +
//                "touch " + path + "/hazelcast/bin/log-" + nodeName + ".out && " +
//                "nohup java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
//                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " +
//                "com.hazelcast.core.server.StartServer > " +
//                path + "/hazelcast/bin/log-" + nodeName + ".null 2> " +
//                path + "/hazelcast/bin/log-" + nodeName + ".out < /dev/null & echo $!";
        return  "java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " +
                "com.hazelcast.core.server.StartServer & echo $!";
    }

}
