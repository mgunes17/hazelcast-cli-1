package com.hazelcast.cli;

import com.pastdev.jsch.scp.ScpFile;
import joptsimple.OptionSet;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.AbstractMap;
import java.util.Set;

import static com.hazelcast.cli.CLI.files;
import static com.hazelcast.cli.CLI.sessions;

public class CommandStartMember {

    static int counter = 1;

    public static void apply(OptionSet result, ClusterSettings settings, Set<HostSettings> machines) throws Exception {

        if (!ControlUtil.checkCredentials()) {
            return;
        }
        if (machines.size() == 0) {
            System.out.println("You don't have any hosts configured.\n");
            return;
        }
        String machineName = (String) result.valueOf("start-member");
        if (machineName == null) {
            System.out.println("No machine name is given.");
            System.out.println("Usage: start-member [host] -t [tag]");
            return;
        }

        HostSettings machine = HostSettings.getMachine(result, machines, machineName);

        if (machine == null) {
            System.out.println("Invalid hostname, please try again.");
            System.out.println("Usage: start-member [host] -t [tag]");
            return;
        }

        try {
            if (CLI.firstMember.get(settings.clusterName) == null) {
                settings.user = machine.userName;
                settings.hostIp = machine.hostIp;
                settings.identityPath = machine.identityPath;
                settings.port = machine.sshPort;
                settings.memberPort = "5701";
                CLI.firstMember.put(settings.clusterName, machineName);
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
            if (nodeName != null && CLI.members.get(nodeName) != null) {
                System.out.println("This tag already exist, please try again");
                System.out.println("Usage: start-member [host] -t [tag]");
                return;
            }
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
//            System.out.println("Uploading config file at path " + configFile);

            Runtime.getRuntime().exec(cmd);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Upload completed.");
            System.out.println("Starting instance...");
            String startCmd = start(remotePath, nodeName);

            //TODO: can I assume that java is already installed to Amazon EC2 ?
            String pid = SshExecutor.exec(user, hostIp, port, startCmd, true, identityPath, false);
            String hostport = "";
            while (hostport.equals("") || hostport.equals("null")) {
                hostport = SshExecutor.exec(user, hostIp, port, "cat " + remotePath + "/ports/" + nodeName, true, identityPath, false);
            }
            SshExecutor.exec(user, hostIp, port, "rm " + remotePath + "/ports/" + nodeName, true, identityPath, false);
            CLI.members.put(nodeName, new AbstractMap.SimpleEntry(machineName, hostport));

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
            File file = files.get(machineName);
            FileUtils.writeStringToFile(file, hostport + " " + nodeName + System.lineSeparator(), true);
            ScpFile scpFile = new ScpFile(sessions.get(machineName),
                    "/home", "ubuntu", "hazelcast", "members.txt");
            scpFile.copyFrom(file);
        } catch (Exception e) {
            System.out.println("Please try starting a member again.");
        }

    }

    private static String upload(String user, String hostName, String nodeName, String configFile, String path, String identityPath) {
        if (configFile == null) {
            configFile = path + "hazelcast/bin/hazelcast.xml";
        }
        return "scp -i " + identityPath + " " + configFile + " " + user + "@" + hostName + ":" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml";
    }

    private static String start(String path, String nodeName) {
        //java -cp "lib/*" -Dhazelcast.config=hazelcast-istanbul.xml com.hazelcast.core.server.StartServer & echo $!
//        return "touch " + path + "/hazelcast/bin/log-" + nodeName + ".null && " +
//                "touch " + path + "/hazelcast/bin/log-" + nodeName + ".out && " +
//                "java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
//                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " +
//                "com.hazelcast.core.server.StartServer > " +
//                path + "/hazelcast/bin/log-" + nodeName + ".null 2> " +
//                path + "/hazelcast/bin/log-" + nodeName + ".out < /dev/null & echo $!";
        return "java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " + "-Dprint.port=" + nodeName + " " +
                "com.hazelcast.core.server.StartServer & echo $!";
    }

}
