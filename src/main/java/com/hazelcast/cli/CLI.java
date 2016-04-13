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

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CLI {

    private static ConsoleReader reader;
    public static String currentCluster = "";
    public static String currentClusterPassword = "";
    public static Map<String, String> firstMember = new HashMap<String, String>();

    public static void main(String[] args) throws Exception {

        reader = new ConsoleReader();
        System.out.println((new HazelcastArt()).art);
        System.out.println(
                "Welcome to Hazelcast command line interface.\n" +
                        "Type --help to see command options.");
        mainConsole();

    }

    private static void mainConsole() throws Exception {

        Boolean open = true;
        CommandOptions commandOptions = new CommandOptions();
        ClusterSettings settings = new ClusterSettings();
        Set<MachineSettings> machines = new HashSet<MachineSettings>();

        while (open) {

            try {
                String input = reader.readLine("hz " + currentCluster + "-> ");
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
                        CommandStartMember.apply(result, machines);
                    } else if (result.has(commandOptions.addMachine)) {
                        CommandAddMachine.apply(reader, machines, (String) result.valueOf("add-machine"));
                    } else if (result.has(commandOptions.removeMachine)) {
                        CommandRemoveMachine.apply(result, machines);
                    } else if (result.has(commandOptions.listMachines)) {
                        CommandListMachines.apply(machines);
                    } else if (result.has(commandOptions.clusterConnect)) {
                        settings = CommandClusterConnect.apply(reader);
                    } else if (result.has(commandOptions.clusterDisconnect)) {
                        settings = CommandClusterDisconnect.apply();
                    } else if (result.has(commandOptions.shutdownCluster)) {
                        CommandClusterShutdown.apply(result, machines,settings);
                    } else if (result.has(commandOptions.killMember)) {
                        CommandClusterKillMember.apply(result, settings);
                    } else if (result.has(commandOptions.listMember)) {
                        CommandClusterListMember.apply(result, settings);
                    } else if (result.has(commandOptions.getClusterState)) {
                        CommandClusterGetState.apply(result, settings);
                    } else if (result.has(commandOptions.changeClusterState)) {
                        CommandClusterChangeState.apply(result, settings);
                    } else if (result.has(commandOptions.changeClusterSettings)) {
                        CommandClusterChangeSettings.apply(result, settings);
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
                System.out.println("Please try again.");
            }
        }
    }

}

