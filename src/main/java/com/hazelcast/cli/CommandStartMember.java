package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;

public class CommandStartMember {

    static int counter = 1;

    public static void apply(OptionSet result, Set<MachineSettings> machines) throws Exception {

//        if(!properties.isConnectedToMachine) {
//            System.out.println(
//                "Please first connect to a machine by typing " +
//                "--connect-machine --user <user> --ip <ip> --remote-path <absolute path for hazelcast> --identity-path <password path>.");
//            return;
//        }
        if (machines.size() == 0) {
            System.out.println(
                    "You don't have any machines configured.\n" +
                            "Please first add a host machine with the command '--add-machine'.");
            return;
        }
        String machineName;
        try {
            machineName = (String) result.valueOf("start-member");
        } catch (Exception e) {
            System.out.println("No machine name is given.");
            return;
        }

        MachineSettings machine = MachineSettings.getMachine(result, machines, machineName);

        if (machine == null) {
            System.out.println("Please try again to start a member.");
            return;
        }

        try {
            if (CLI.firstMember.get(CLI.currentCluster) == null) {
                CLI.firstMember.put(CLI.currentCluster, machineName);
            }
            String user = machine.userName;
            String hostIp = machine.hostIp;
            int port = machine.sshPort;
            String remotePath = machine.remotePath;
            String identityPath = machine.identityPath;

            String configFile = (String) result.valueOf(com.hazelcast.cli.CommandOptions.optionConfigFile);
            if (configFile == null) {
                configFile = CLI.class.getClassLoader().getResource("hazelcast.xml").getFile();
            }
            String nodeName = (String) result.valueOf(com.hazelcast.cli.CommandOptions.optionNodeName);
            if (nodeName == null) {
                nodeName = "node" + counter++;
            }

//            Config config = new FileSystemXmlConfig(configFile);
//
//            config.getNetworkConfig().setPublicAddress(hostIp);
//            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
//            if (CLI.instanceAdresses.get(CLI.currentCluster) == null) {
//                CLI.instanceAdresses.put(CLI.currentCluster, new ArrayList<String>());
//            }
//            for (String ip : CLI.instanceAdresses.get(CLI.currentCluster)) {
//                config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(ip);
//            }
//            CLI.instanceAdresses.get(CLI.currentCluster).add(hostIp);
//
//            String xml = new ConfigXmlGenerator(true).generate(config);
//            File temp = File.createTempFile("tempfile", ".tmp");
//            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
//            bw.write(xml);
//            bw.close();

            String cmd = upload(user, hostIp, nodeName, configFile, remotePath, identityPath);
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

            //TODO: can I assume that java is already installed to Amazon EC2 ?
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

        } catch (Exception e) {
            System.out.println("Please try starting a member again.");
        }

    }

    private static String upload(String user, String hostName, String nodeName, String configFile, String path, String identityPath) {
        if (configFile == null) {
            configFile = path + "hazelcast/bin/hazelcast.xml";
        }
        //scp bin/hazelcast.xml mefeakengin@localhost:~/hazelcast-istanbul.xml
        return "scp -i " + identityPath + " " + configFile + " " + user + "@" + hostName + ":" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml";
    }

    private static String start(String path, String nodeName) {
        //java -cp "lib/*" -Dhazelcast.config=hazelcast-istanbul.xml com.hazelcast.core.server.StartServer & echo $!
        return "touch " + path + "/hazelcast/bin/log-" + nodeName + ".null && " +
                "touch " + path + "/hazelcast/bin/log-" + nodeName + ".out && " +
                "java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " +
                "com.hazelcast.core.server.StartServer > " +
                path + "/hazelcast/bin/log-" + nodeName + ".null 2> " +
                path + "/hazelcast/bin/log-" + nodeName + ".out < /dev/null & echo $!";
//        return "java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
//                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " +
//                "com.hazelcast.core.server.StartServer & echo $!";
    }

}