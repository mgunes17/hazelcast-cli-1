package com.hazelcast.cli;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.util.Set;

public class CommandInstall {

    public static void apply (OptionSet result, Set<MachineSettings> machines) throws Exception {

//        if(!properties.isConnectedToMachine) {
//            System.out.println("Please first connect to a machine by typing " +
//                    "--connect-machine --user <user> --ip <ip> --remote-path <absolute path for hazelcast> --identity-path <password path>.");
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
            machineName = (String) result.valueOf("install");
        } catch (Exception e) {
            System.out.println("No machine name is given.");
            return;
        }

        MachineSettings machine = MachineSettings.getMachine(result, machines, machineName);

        if (machine == null) {
            return;
        }

        try {
            String user = machine.userName;
            String hostIp = machine.hostIp;
            int port = machine.sshPort;
            String remotePath = machine.remotePath;
            String identityPath = machine.identityPath;

            OptionSpec version = com.hazelcast.cli.CommandOptions.version;

//            //Localhost
//            //TODO: Don't loose time with this. Localhost is not working yet.
            if ((hostIp.equals("localhost") || hostIp.equals("127.0.0.1"))) {
                System.out.println("Working at localhost");
                if (!result.has(version)) {
                    System.out.println("--version required");
                }
                String strVersion = (String) result.valueOf(version);

                String installCommand = buildCommandDownload(strVersion, remotePath);
                System.out.println("downloadCommand " + installCommand);
                System.out.println("Download started...");
                String installOutput = LocalExecutor.exec(installCommand, false);
                System.out.println("installOutput: " + installOutput);

                System.out.println("Extracting...");
                String extractCommand = buildCommandExtract(remotePath);
                String extractOutput = LocalExecutor.exec(extractCommand, false);
                System.out.println("extractOutput: " + extractOutput);

                String move = buildCommandMove(remotePath + "/hazelcast-" + strVersion, remotePath + "/hazelcast");
                LocalExecutor.exec(move, false);
                System.out.println("Installation completed...");
            }

            //Remotehost
            else {

                if (!result.has(version)) {
                    System.out.println("\"--hazelcast-version <version>\" is required");
                    return;
                }
                String strVersion = (String) result.valueOf(version);
                String command = buildCommandDownload(strVersion, remotePath);
                System.out.println("Download started...");
                SshExecutor.exec(user, hostIp, port, command, false, identityPath);
                System.out.println("Extracting...");
                String extractCommand = buildCommandExtract(remotePath);
                SshExecutor.exec(user, hostIp, port, extractCommand, false, identityPath);

                String move = buildCommandMove(remotePath + "/hazelcast-" + strVersion, remotePath + "/hazelcast-all");
                SshExecutor.exec(user, hostIp, port, move, false, identityPath);
                SshExecutor.exec(user, hostIp, port, "mkdir " + remotePath + "/hazelcast", false, identityPath);
                SshExecutor.exec(user, hostIp, port, "mkdir " + remotePath + "/hazelcast/bin", false, identityPath);
                String renameJar = buildCommandMove(remotePath + "/hazelcast-all/lib/hazelcast-" + strVersion + ".jar", remotePath + "/hazelcast/hazelcast.jar");
                SshExecutor.exec(user, hostIp, port, renameJar, false, identityPath);
                String renameManagementCenter = buildCommandMove(remotePath + "/hazelcast-all/mancenter/mancenter-" + strVersion + ".war", remotePath + "/hazelcast/mancenter.war");
                SshExecutor.exec(user, hostIp, port, renameManagementCenter, false, identityPath);

                System.out.println("Download of Hazelcast " + strVersion + " JAR files, Reference Manual & Javadocs, Code Samples, demo files, and Management Center WAR file " +
                        "is completed under the path " + remotePath + "/hazelcast-all");
                System.out.println("Hazelcast version " + strVersion + " installation is completed under the path " + remotePath + "/hazelcast");
            }
        } catch (Exception e) {
            System.out.println("Please try installing Hazelcast again.");
        }

    }

    private static final String HZ_DOWNLOAD_URL =
            "\"http://download.hazelcast.com/download.jsp?version=hazelcast-#version#&type=tar&p=153008119\"";
//    http://download.hazelcast.com/download.jsp?version=hazelcast-3.6-EA3&p=153008119

    private static String buildCommandDownload(String version, String path) {
        String url = HZ_DOWNLOAD_URL.replace("#version#", version);
        //for osx curl -o /Users/mefeakengin/hazelcast/hazelcast.tar.gz 'http://download.hazelcast.com/download.jsp?version=hazelcast-3.5.4&type=tar&p=153008119'
//        return "curl -o ~/hazelcast/hazelcast" + ".tar.gz "  + url;
        return "wget -O " + path + "/hazelcast" + ".tar.gz " + url ;
    }

    private static String buildCommandExtract(String path) {
        return "tar -zxvf " + path + "/hazelcast" + ".tar.gz";
    }

    private static String buildCommandMove(String original, String target) {
        return "cp -r " + original + " " + target;
    }

}
