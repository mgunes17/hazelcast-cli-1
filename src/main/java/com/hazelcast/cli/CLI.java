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

import com.jcraft.jsch.JSchException;
import com.pastdev.jsch.DefaultSessionFactory;
import com.pastdev.jsch.scp.ScpFile;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import java.io.File;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CLI {

    private static ConsoleReader reader;
    public static Map<String, String> firstMember = new HashMap<String, String>();
    public static Map<String, AbstractMap.SimpleEntry<String, String>> members = new HashMap<String, AbstractMap.SimpleEntry<String, String>>();
    public static Set<MachineSettings> machines;
    public static HashMap<String, File> files = new HashMap<String, File>();
    public static HashMap<String, DefaultSessionFactory> sessions = new HashMap<String, DefaultSessionFactory>();

    public static void main(String[] args) throws Exception {

        reader = new ConsoleReader();
        System.out.println((new HazelcastArt()).art);
        System.out.println(
                "Welcome to Hazelcast command line interface.\n" +
                        "Type help to see command options.");
        mainConsole();

    }

    private static void mainConsole() throws Exception {

        Boolean open = true;
        CommandOptions commandOptions = new CommandOptions();
        ClusterSettings settings = new ClusterSettings();
        machines = new HashSet<MachineSettings>();
        addMachines(machines);
        readMemberInfoFile();
        while (open) {

            try {
                String input = reader.readLine("hz " + settings.clusterName + "-> ");
                if (!input.startsWith("-")) {
                    input = "-" + input;
                }
                OptionSet result = commandOptions.parse(input);

                if (result.has(commandOptions.help)) {
                    CommandHelp.apply();
                } else {
                    if (result.has(commandOptions.install)) {
                        //TODO: Handle properties set for local/remote
                        CommandInstall.apply(result, machines);
                    } else if (result.has(commandOptions.startMember)) {
                        CommandStartMember.apply(result, settings, machines);
//                    } else if (result.has(commandOptions.addMachine)) {
//                        CommandAddMachine.apply(reader, machines, (String) result.valueOf("add-machine"));
                    } else if (result.has(commandOptions.removeMachine)) {
                        CommandRemoveMachine.apply(result, machines);
                    } else if (result.has(commandOptions.listMachines)) {
                        CommandListMachines.apply(machines);
                    } else if (result.has(commandOptions.setCredentials)) {
                        settings = CommandClusterConnect.apply(result, reader);
                    } else if (result.has(commandOptions.clusterDisconnect)) {
                        settings = CommandClusterDisconnect.apply();
                    } else if (result.has(commandOptions.shutdownCluster)) {
                        CommandClusterShutdown.apply(result, settings);
                    } else if (result.has(commandOptions.killMember)) {
                        CommandClusterKillMember.apply(result, machines, settings);
                    } else if (result.has(commandOptions.forceStart)) {
                        CommandForceStartMember.apply(result, machines, settings);
                    } else if (result.has(commandOptions.listMember)) {
                        CommandClusterListMember.apply(result, settings);
                    } else if (result.has(commandOptions.getClusterState)) {
                        CommandClusterGetState.apply(result, settings);
                    } else if (result.has(commandOptions.changeClusterState)) {
                        CommandClusterChangeState.apply(result, settings);
                    } else if (result.has(commandOptions.changeClusterSettings)) {
                        CommandClusterChangeSettings.apply(result, reader, machines, settings);
                    } else if (result.has(commandOptions.startManagementCenter)) {
                        CommandManagementCenterStart.apply(result, settings);
                    } else if (result.has(commandOptions.exit)) {
                        CommandExitProgram.apply();
                        open = false;
                    } else if (!input.equals("")) {
                        System.out.println("Command not valid. Please type --help to see valid command options");
                    }
                }
            } catch (Exception e) {
//                System.out.println("Please try again.");
            }
        }
    }

    private static void readMemberInfoFile() throws Exception {

        for (MachineSettings machineSetting : machines) {
            DefaultSessionFactory defaultSessionFactory = new DefaultSessionFactory(
                    machineSetting.userName, machineSetting.hostIp, machineSetting.sshPort);
            try {
//                defaultSessionFactory.setKnownHosts( knownHosts );
                defaultSessionFactory.setIdentityFromPrivateKey(machineSetting.identityPath);
            } catch (JSchException e) {
                System.out.println("error");
//                Assume.assumeNoException( e );
            }
            sessions.put(machineSetting.machineName, defaultSessionFactory);
//            File toFile = new File(dir, toFilename);
            try {
                ScpFile to = new ScpFile(defaultSessionFactory,
                        "/home/ubuntu/hazelcast/members.txt");
                File file = File.createTempFile("members", ".tmp");
                to.copyTo(file);
                files.put(machineSetting.machineName, file);
            } catch (Exception e) {
            }
//            String command = "scp -t " + machineSetting.remotePath + "/hazelcast/members.txt";
//            String out = SshExecutor.exec(
//                    machineSetting.userName,
//                    machineSetting.hostIp,
//                    machineSetting.sshPort,
//                    command,
//                    false,
//                    machineSetting.identityPath,
//                    false);
//            System.out.println(out);
        }
    }

    private static void addMachines(Set<MachineSettings> machines) throws Exception {
        HashMap<String, String> hashMap = new HashMap();
        Set<String> set = new HashSet();
        Properties prop = new Properties();
        InputStream is = CLI.class.getClassLoader().getResourceAsStream("cli.properties");
        prop.load(is);
        Enumeration it = prop.propertyNames();
        while (it.hasMoreElements()) {
            String token = (String) it.nextElement();
            String key = token.split("\\.")[0];
            String value = token.split("\\.")[1];
            hashMap.put(token, prop.getProperty(token));
            set.add(key);
        }

        for (String key : set) {
            String userName = hashMap.get(key + ".user");
            String hostIp = hashMap.get(key + ".ip");
            String remotePath = hashMap.get(key + ".remotePath");
            String identityPath = hashMap.get(key + ".identityPath");
            MachineSettings machine = new MachineSettings(key, userName, hostIp, remotePath, identityPath);

            System.out.println("Connection settings set for " + machine.userName + "@" + machine.hostIp);
            String message = SshExecutor.exec(machine.userName, machine.hostIp, 22, "", false, machine.identityPath, false);
            if ((message == null) || (!message.equals("exception"))) {
                System.out.println("Machine " + machine.machineName + " is added.");
                machines.add(machine);
            } else {
                System.out.println("Could not connect to the machine.");
                System.out.println("Please try to add a machine again.");
            }
        }

    }

}

