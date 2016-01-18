/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.cli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

public class CLI {

    public static void main(String[] args) throws Exception {

        OptionParser optionParser = new OptionParser();

        //Setting operations for the command
        OptionSpec help = optionParser.acceptsAll(Arrays.asList("help", "h"));
        OptionSpec install = optionParser.acceptsAll(Arrays.asList("install", "i"));
        OptionSpec start = optionParser.acceptsAll(Arrays.asList("s", "start"));
        OptionSpec startMC = optionParser.acceptsAll(Arrays.asList("start-mc", "management-center"));
        OptionSpec hostName = optionParser.acceptsAll(Arrays.asList("H", "hostname")).withRequiredArg().ofType(String.class);
        OptionSpec version = optionParser.acceptsAll(Arrays.asList("v", "version")).withRequiredArg().ofType(String.class);

        //Options of operations
        OptionSpec optionClusterName = optionParser.acceptsAll(Arrays.asList("c", "cluster-name")).withRequiredArg().ofType(String.class);
        OptionSpec optionNodeName = optionParser.acceptsAll(Arrays.asList("n", "node-name")).withRequiredArg().ofType(String.class);
        OptionSpec optionConfigFile = optionParser.acceptsAll(Arrays.asList("C", "config-file")).withRequiredArg().ofType(String.class);
        OptionSpec optionGroupName = optionParser.acceptsAll(Arrays.asList("g", "group-name")).withRequiredArg().ofType(String.class);
        OptionSpec optionPassword = optionParser.acceptsAll(Arrays.asList("P", "password")).withRequiredArg().ofType(String.class);
        OptionSpec optionClusterPort = optionParser.acceptsAll(Arrays.asList("p", "port")).withRequiredArg().ofType(String.class);

        //Options for cluster operations
        OptionSpec shutdown = optionParser.acceptsAll(Arrays.asList("shutdown", "S"));
        OptionSpec killNode = optionParser.acceptsAll(Arrays.asList("k", "kill-node"));
        OptionSpec listNodes = optionParser.acceptsAll(Arrays.asList("l", "list-node"));
        OptionSpec state = optionParser.acceptsAll(Arrays.asList("cluster-state"));
        OptionSpec changeState = optionParser.acceptsAll(Arrays.asList("change-state")).withRequiredArg().ofType(String.class);;

        OptionSet result = optionParser.parse(args);

        //Building the user and other properties
        if(!result.has(hostName) && !result.has(help)){
            System.out.println("Host name is required. \n");
            System.exit(-1);
        } else if (!result.has(help)) {

            String host = (String) result.valueOf(hostName);
            String userHome = System.getProperty("user.home");
            Properties properties = getProperties(userHome);
            String user = properties.getProperty(host + ".user");
            String hostIp = properties.getProperty(host + ".ip");
            int port = Integer.parseInt(properties.getProperty(host + ".port"));
            String remotePath = properties.getProperty(host + ".remotePath");
            String identityPath = properties.getProperty(host + ".identityPath");

            CommandBuilder commandBuilder = new CommandBuilder();


            //executing the command
            if (result.has(install)) {

    //            //Localhost
    //            //TODO: Don't loose time with this
                if((hostIp.equals("localhost")||hostIp.equals("127.0.0.1"))){
                    System.out.println("Working at localhost");
                    if (!result.has(version)) {
                        System.out.println("--version required");
                        System.exit(-1);
                    }
                    String strVersion = (String) result.valueOf(version);

                    String installCommand = commandBuilder.wget(strVersion, remotePath);
                    System.out.println("downloadCommand " + installCommand);
                    System.out.println("Download started...");
                    String installOutput = LocalExecutor.exec(installCommand, false);
                    System.out.println("installOutput: " + installOutput);

                    System.out.println("Extracting...");
                    String extractCommand = commandBuilder.extract(remotePath);
                    String extractOutput = LocalExecutor.exec(extractCommand, false);
                    System.out.println("extractOutput: " + extractOutput);

                    String move = commandBuilder.move(remotePath + "/hazelcast-" + strVersion, remotePath + "/hazelcast");
                    LocalExecutor.exec(move, false);
                    System.out.println("Installation completed...");
                }

                //Remotehost
                else {
                    if (!result.has(version)) {
                        System.out.println("--version required");
                        System.exit(-1);
                    }
                    System.out.println((new HazelcastArt().art));
                    String strVersion = (String) result.valueOf(version);
                    String command = commandBuilder.wget(strVersion, remotePath);
                    System.out.println("Download started...");
                    String output = SshExecutor.exec(user, hostIp, port, command, false, identityPath).message;
                    System.out.println("Extracting...");
                    String extractCommand = commandBuilder.extract(remotePath);
                    SshExecutor.exec(user, hostIp, port, extractCommand, false, identityPath);

                    String move = commandBuilder.move(remotePath + "/hazelcast-" + strVersion, remotePath + "/hazelcast-all");
                    SshExecutor.exec(user, hostIp, port, move, false, identityPath);
                    SshExecutor.exec(user, hostIp, port, "mkdir " + remotePath + "/hazelcast", false, identityPath);
                    SshExecutor.exec(user, hostIp, port, "mkdir " + remotePath + "/hazelcast/bin", false, identityPath);
                    String renameJar = commandBuilder.move(remotePath + "/hazelcast-all/lib/hazelcast-" + strVersion + ".jar", remotePath + "/hazelcast/hazelcast.jar");
                    SshExecutor.exec(user, hostIp, port, renameJar, false, identityPath);
                    String renameManagementCenter = commandBuilder.move(remotePath + "/hazelcast-all/mancenter/mancenter-" + strVersion + ".war", remotePath + "/hazelcast/mancenter.war");
                    SshExecutor.exec(user, hostIp, port, renameManagementCenter, false, identityPath);

                    System.out.println("Download of Hazelcast " + strVersion + " JAR files, Reference Manual & Javadocs, Code Samples, demo files, and Management Center WAR file " +
                            "is completed under the path " + remotePath + "/hazelcast-all");
                    System.out.println("Hazelcast version " + strVersion + " installation is completed under the path " + remotePath + "/hazelcast");
                }

            } else if (result.has(start)) {
                if (!result.has(optionConfigFile)) {
                    System.out.println("A config file is required. \n");
                    System.exit(-1);
                }
                String clusterName = (String) result.valueOf(optionClusterName);
                String nodeName = (String) result.valueOf(optionNodeName);
                String configFile = (String) result.valueOf(optionConfigFile);
                String cmd = commandBuilder.upload(user, hostIp, nodeName, configFile, remotePath);
                System.out.println("Uploading config file " + configFile);

                Runtime.getRuntime().exec(cmd);

                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Upload completed.");
                System.out.println("Starting instance...");
                String startCmd = commandBuilder.start(remotePath, nodeName);

                //TODO: can I assume that java is already installed to Amazon EC2
                SshReturn returnValue = SshExecutor.exec(user, hostIp, port, startCmd, true, identityPath);

    //            //TODO: Standardize "/log.out"
    //            int oldLogLength = returnValue.lineCounter;
    //            String copyLogCmd = commandBuilder.copyLog(user, hostIp, remotePath, localPath);
    ////            SshExecutor.exec(user, hostIp, port, copyLogCmd, true, remotePath);
    //            System.out.println("Copy the log command: " + copyLogCmd);
    //            Runtime.getRuntime().exec(copyLogCmd);
    //
    //            String printLog = commandBuilder.printLog(localPath, oldLogLength);
    //            System.out.println("Print the most current log: " + printLog);
    //            Runtime.getRuntime().exec(printLog);

                String pid = returnValue.message;
                System.out.println("Instance started : " + pid);

            } else if (result.has(startMC)) {
                SshExecutor.exec(user, hostIp, port, commandBuilder.startMC(remotePath, hostIp), false, identityPath);

            } else if (result.has(shutdown)) {
                String groupName;
                if (result.has(optionGroupName)) {
                    groupName = (String) result.valueOf(optionGroupName);
                } else {
                    groupName = "dev";
                    System.out.println("Group name is not specified, default group name is set to: " + groupName);
                }

                String password;
                if (result.has(optionPassword)) {
                    password = (String) result.valueOf(optionPassword);
                } else {
                    password = "dev-pass";
                    System.out.println("Password is not specified, default password is set to: " + password);
                }

                String clusterPort;
                if (result.has(optionClusterPort)) {
                    clusterPort = (String) result.valueOf(optionClusterPort);
                } else {
                    clusterPort = "5701";
                    System.out.println("Port is not specified, default port is set to: " + clusterPort);
                }

                String shutdownCmd = commandBuilder.shutdown(hostIp, clusterPort, groupName, password);

                SshExecutor.exec(user, hostIp, port, shutdownCmd, false, identityPath);

            } else if (result.has(listNodes)) {
                String groupName;
                if (result.has(optionGroupName)) {
                    groupName = (String) result.valueOf(optionGroupName);
                } else {
                    groupName = "dev";
                    System.out.println("Group name is not specified, default group name is set to: " + groupName);
                }

                String password;
                if (result.has(optionPassword)) {
                    password = (String) result.valueOf(optionPassword);
                } else {
                    password = "dev-pass";
                    System.out.println("Password is not specified, default password is set to: " + password);
                }

                String clusterPort;
                if (result.has(optionClusterPort)) {
                    clusterPort = (String) result.valueOf(optionClusterPort);
                } else {
                    clusterPort = "5701";
                    System.out.println("Port is not specified, default clusterPort is set to: " + clusterPort);
                }

                String listNodesCmd = commandBuilder.listNodes(hostIp, clusterPort, groupName, password);

                SshExecutor.exec(user, hostIp, port, listNodesCmd, false, identityPath);

            } else if (result.has(killNode)) {
                String groupName;
                if (result.has(optionGroupName)) {
                    groupName = (String) result.valueOf(optionGroupName);
                } else {
                    groupName = "dev";
                    System.out.println("Group name is not specified, default group name is set to: " + groupName);
                }

                String password;
                if (result.has(optionPassword)) {
                    password = (String) result.valueOf(optionPassword);
                } else {
                    password = "dev-pass";
                    System.out.println("Group name is not specified, default password is set to: " + password);
                }

                String clusterPort;
                if (result.has(optionClusterPort)) {
                    clusterPort = (String) result.valueOf(optionClusterPort);
                } else {
                    clusterPort = "5701";
                    System.out.println("Group name is not specified, default clusterPort is set to: " + clusterPort);
                }

                String killNodeCmd = commandBuilder.killNode(hostIp, clusterPort, groupName, password);

                SshExecutor.exec(user, hostIp, port, killNodeCmd, false, identityPath);

            } else if (result.has(state)) {
                String groupName;
                if (result.has(optionGroupName)) {
                    groupName = (String) result.valueOf(optionGroupName);
                } else {
                    groupName = "dev";
                    System.out.println("Group name is not specified, default group name is set to: " + groupName);
                }

                String password;
                if (result.has(optionPassword)) {
                    password = (String) result.valueOf(optionPassword);
                } else {
                    password = "dev-pass";
                    System.out.println("Group name is not specified, default password is set to: " + password);
                }

                String clusterPort;
                if (result.has(optionClusterPort)) {
                    clusterPort = (String) result.valueOf(optionClusterPort);
                } else {
                    clusterPort = "5701";
                    System.out.println("Group name is not specified, default clusterPort is set to: " + clusterPort);
                }

                String stateCmd = commandBuilder.state(hostIp, clusterPort, groupName, password);

                SshExecutor.exec(user, hostIp, port, stateCmd, false, identityPath);

            } else if (result.has(changeState)) {

                String stateParam = ((String) result.valueOf(changeState)).toLowerCase();

                if (!(stateParam.equals("active") || stateParam.equals("passive") || stateParam.equals("frozen"))) {
                    System.out.println("Invalid change state parameter. State parameter should be one of active, passive or frozen \n");
                    System.exit(-1);
                }

                String groupName;
                if (result.has(optionGroupName)) {
                    groupName = (String) result.valueOf(optionGroupName);
                } else {
                    groupName = "dev";
                    System.out.println("Group name is not specified, default group name is set to: " + groupName);
                }

                String password;
                if (result.has(optionPassword)) {
                    password = (String) result.valueOf(optionPassword);
                } else {
                    password = "dev-pass";
                    System.out.println("Group name is not specified, default password is set to: " + password);
                }

                String clusterPort;
                if (result.has(optionClusterPort)) {
                    clusterPort = (String) result.valueOf(optionClusterPort);
                } else {
                    clusterPort = "5701";
                    System.out.println("Group name is not specified, default clusterPort is set to: " + clusterPort);
                }

                String stateCmd = commandBuilder.changeState(hostIp, clusterPort, groupName, password, stateParam);

                SshExecutor.exec(user, hostIp, port, stateCmd, false, identityPath);
            } else {
                System.out.println("Please type --help to see valid commands");
            }

        } else if (result.has(help)) {
            System.out.println((new HazelcastArt()).art);
            System.out.println(
                "This command line interface is created for managing and provisioning of hazelcast clusters and nodes.\n" +
                "Following commands are possible: \n" +
                "--change-state                     : Changes the state of the cluster. Change parameter should be specified as one of active, passive or frozen.\n" +
                "--cluster-state                    : Gets the state of the cluster. Cluster state is active, passive or frozen.\n" +
                "--config-file, --C                 : Specifies the configuration .xml file for starting a node.\n" +
                "--group-name, --g                  : Specifies the group name. Required for cluster operations. Default is dev.\n" +
                "--help, --h                        : Lists the valid options.\n" +
                "--hostname, -H                     : Specifies the hostname.\n" +
                "--install, --i                     : Downloads and extracts hazelcast with the given version. Version is required.\n" +
                "--kill-node, --k                   : Kills the node at the specified host and port. Hostname, group name, password and port are required.\n" +
                "--list-nodes, --l                  : Lists the node details in a cluster. Group name, password and port are required.\n" +
                "--managament-center, --start-mc    : Starts the managament center with the url given in the .xml file.\n" +
                "--password, --P                    : Specifies the password for the cluster group. Required for cluster operations. Default is dev-pass.\n" +
                "--port, --p                        : Specifies the port for the host IP. Required for cluster and node operations. Default is 5701.\n" +
                "--start, --s                       : Starts a node from a hazelcast configuration .xml file given with the local file path.\n" +
                "--shutdown, --S                    : Shuts down the cluster. The command should be sent to any node in the cluster. Group name, password and port are required.\n" +
                "--version, --v                     : Specifies the version of hazelcast. Example: --version 3.5.4 \n" +
                "\n" +
                " --- Example Commands --- \n" +
                "--hostname ubuntu --start --clustername cluster --nodename node --configfile /Users/user/hazelcast/hazelcast.xml\n" +
                "--hostname ubuntu --shutdown\n" +
                "--hostname ubuntu --killNode --port 5703\n"
            );
        } else {
            System.out.println("Please type --help to see valid commands");
        }
    }

    private static Properties getProperties(String userHome) {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "/hazelcast/cli.properties";
            //load a properties file from class path, inside static method

            prop.load(new InputStreamReader(new FileInputStream(userHome+filename)));
            return prop;

        } catch (IOException ex) {
            System.out.println(
                    "cli.properties file is not set. Please create the file to the path \n" +
                    "<home-directory>/hazelcast/cli.properties with entries as following: \n" +
                    "<hostName>.user=<user>\n" +
                    "<hostName>.ip=<ip>\n" +
                    "<hostName>.port=22\n" +
                    "<hostName>.remotePath=<path to install hazelcast on machine> \n" +
                    "<hostname>.identityPath=<path to rsa id for the machine> \n"
            );
            ex.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

