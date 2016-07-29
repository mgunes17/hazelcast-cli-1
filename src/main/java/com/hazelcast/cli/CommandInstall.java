package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandInstall {
	private static Logger logger = LoggerFactory.getLogger(CommandInstall.class);
	
    public static void apply(OptionSet result, Set<HostSettings> machines) throws Exception {

        if (machines.size() == 0) {
        	logger.trace("There is no host");
            System.out.println(
                    "You don't have any hosts configured.\n" +
                            "Please first add a host machine with the command '--add-machine'.");
            return;
        }
        if (result.nonOptionArguments().size() < 2) {
        	logger.trace("Missing parameters");
            System.out.println("Please specify host name and version");
            System.out.println("Usage: install [host] [version]");
            System.out.println("Type help to see command options.");
            return;
        }
        String machineName = (String) result.nonOptionArguments().get(0);

        HostSettings machine = HostSettings.getMachine(result, machines, machineName);

        if (machine == null) {
        	logger.trace("machine is null");
            return;
        }

        logger.info("Reading machine info");
        String user = machine.userName;
        String hostIp = machine.hostIp;
        int port = machine.sshPort;
        String remotePath = machine.remotePath;
        String identityPath = machine.identityPath;

        String strVersion = (String) result.nonOptionArguments().get(1);
        String command = buildCommandDownload(strVersion, remotePath);
        System.out.println("Download started...");
        SshExecutor.exec(user, hostIp, port, command, false, identityPath, false);
        System.out.println("Extracting...");
        String extractCommand = buildCommandExtract(remotePath);
        SshExecutor.exec(user, hostIp, port, extractCommand, false, identityPath, false);

        logger.info("Ssh is executing");
        String move = buildCommandMove(remotePath + "/hazelcast-" + strVersion, remotePath + "/hazelcast-all");
        SshExecutor.exec(user, hostIp, port, move, false, identityPath, false);
        SshExecutor.exec(user, hostIp, port, "mkdir " + remotePath + "/hazelcast", false, identityPath, false);
        SshExecutor.exec(user, hostIp, port, "mkdir " + remotePath + "/ports", false, identityPath, false);
        SshExecutor.exec(user, hostIp, port, "mkdir " + remotePath + "/hazelcast/bin", false, identityPath, false);
        String renameJar = buildCommandMove(remotePath + "/hazelcast-all/lib/hazelcast-" + strVersion + ".jar", remotePath + "/hazelcast/hazelcast.jar");
        SshExecutor.exec(user, hostIp, port, renameJar, false, identityPath, false);
        String renameManagementCenter = buildCommandMove(remotePath + "/hazelcast-all/mancenter/mancenter-" + strVersion + ".war", remotePath + "/hazelcast/mancenter.war");
        SshExecutor.exec(user, hostIp, port, renameManagementCenter, false, identityPath, false);

        System.out.println("Download of Hazelcast " + strVersion + " JAR files, Reference Manual & Javadocs, Code Samples, demo files, and Management Center WAR file " +
                "is completed under the path " + remotePath + "/hazelcast-all");
        System.out.println("Hazelcast version " + strVersion + " installation is completed under the path " + remotePath + "/hazelcast");
    }


    private static final String HZ_DOWNLOAD_URL =
            "\"http://download.hazelcast.com/download.jsp?version=hazelcast-#version#&type=tar&p=153008119\"";
//    http://download.hazelcast.com/download.jsp?version=hazelcast-3.6-EA3&p=153008119

    private static String buildCommandDownload(String version, String path) {
    	logger.trace("Buil command download");
        String url = HZ_DOWNLOAD_URL.replace("#version#", version);
        //for osx curl -o /Users/mefeakengin/hazelcast/hazelcast.tar.gz 'http://download.hazelcast.com/download.jsp?version=hazelcast-3.5.4&type=tar&p=153008119'
//        return "curl -o ~/hazelcast/hazelcast" + ".tar.gz "  + url;
        return "wget -O " + path + "/hazelcast" + ".tar.gz " + url;
    }

    private static String buildCommandExtract(String path) {
    	logger.trace("Build command Extract");
        return "tar -zxvf " + path + "/hazelcast" + ".tar.gz";
    }

    private static String buildCommandMove(String original, String target) {
    	logger.trace("Build command move");
        return "cp -r " + original + " " + target;
    }

}
