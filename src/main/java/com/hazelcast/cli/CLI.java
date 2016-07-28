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

import java.io.File;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.jcraft.jsch.JSchException;
import com.pastdev.jsch.DefaultSessionFactory;
import com.pastdev.jsch.scp.ScpFile;

import command.collection.list.CommandListAdd;
import command.collection.list.CommandListClear;
import command.collection.list.CommandListContains;
import command.collection.list.CommandListGet;
import command.collection.list.CommandListGetAll;
import command.collection.list.CommandListGetMany;
import command.collection.list.CommandListRemove;
import command.collection.list.CommandListSet;
import command.collection.list.CommandListSize;
import command.collection.map.CommandMapClear;
import command.collection.map.CommandMapDestroy;
import command.collection.map.CommandMapGet;
import command.collection.map.CommandMapGetAll;
import command.collection.map.CommandMapKeys;
import command.collection.map.CommandMapLock;
import command.collection.map.CommandMapPut;
import command.collection.map.CommandMapRemove;
import command.collection.map.CommandMapSize;
import command.collection.map.CommandMapTryLock;
import command.collection.map.CommandMapUnlock;
import command.collection.memory.CommandMemoryAll;
import command.collection.memory.CommandMemoryList;
import command.collection.memory.CommandMemoryMap;
import command.collection.memory.CommandMemoryQueue;
import command.collection.memory.CommandMemorySet;
import command.collection.queue.CommandQueueClear;
import command.collection.queue.CommandQueueOffer;
import command.collection.queue.CommandQueueOfferMany;
import command.collection.queue.CommandQueuePeek;
import command.collection.queue.CommandQueuePoll;
import command.collection.queue.CommandQueuePollMany;
import command.collection.queue.CommandQueueSize;
import command.collection.set.CommandSetAdd;
import command.collection.set.CommandSetClear;
import command.collection.set.CommandSetGetAll;
import command.collection.set.CommandSetRemove;
import command.collection.set.CommandSetSize;
import command.namespace.CommandNSGet;
import command.namespace.CommandNSReset;
import command.namespace.CommandNSSet;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class CLI {

    private static ConsoleReader reader;
    public static Map<String, String> firstMember = new HashMap<String, String>();
    public static Map<String, AbstractMap.SimpleEntry<String, String>> members = new HashMap<String, AbstractMap.SimpleEntry<String, String>>();
    public static Set<HostSettings> hosts = new HashSet<HostSettings>();
    public static HashMap<String, File> files = new HashMap<String, File>();
    public static HashMap<String, DefaultSessionFactory> sessions = new HashMap<String, DefaultSessionFactory>();
    public static ClusterSettings settings;
    public static String nameSpace;
    public static HazelcastInstance instance;
        
    public static void main(String[] args) throws Exception {
        reader = new ConsoleReader();
        System.out.println((new HazelcastArt()).art);
        System.out.println(
                "Welcome to Hazelcast command line interface.\n" +
                        "Type help to see command options.");
        
        Config config = new Config();
		PropertiesFile po = new PropertiesFile("cli.properties");
		ClassLoader classLoader = po.loadClasses();
		
		instance = Hazelcast.newHazelcastInstance(config);
		config.setClassLoader(classLoader);
        mainConsole();
    }

    private static void mainConsole() throws Exception {

        Boolean open = true;
        CommandOptions commandOptions = new CommandOptions();
        settings = new ClusterSettings();
        addHosts(hosts);
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
                        CommandInstall.apply(result, hosts);
                    } else if (result.has(commandOptions.startMember)) {
                        CommandStartMember.apply(result, settings, hosts);
//                    } else if (result.has(commandOptions.host)) {
//                        CommandAddMachine.apply(reader, hosts, (String) result.valueOf("add-host"));
                    } else if (result.has(commandOptions.removeHost)) {
                        CommandRemoveHost.apply(result, hosts);
                    } else if (result.has(commandOptions.listHosts)) {
                        CommandListHosts.apply(hosts);
                    } else if (result.has(commandOptions.setCredentials)) {
                        CommandSetCredentials.apply(result, reader);
                    } else if (result.has(commandOptions.clusterDisconnect)) {
                        CommandClusterDisconnect.apply();
                    } else if (result.has(commandOptions.shutdownCluster)) {
                        CommandClusterShutdown.apply(result, settings);
                    } else if (result.has(commandOptions.CliInfo)) {
                        CommandCliInfo.apply(result, settings);
                    } else if (result.has(commandOptions.killMember)) {
                        CommandShutdownMember.apply(result, hosts, settings);
                    } else if (result.has(commandOptions.forceStart)) {
                        CommandForceStartMember.apply(result, hosts, settings);
                    } else if (result.has(commandOptions.listMember)) {
                        CommandClusterListMember.apply(result, settings);
                    } else if (result.has(commandOptions.listMemberTags)) {
                        CommandListMemberTags.apply();
                    } else if (result.has(commandOptions.getClusterState)) {
                        CommandClusterGetState.apply(result, settings);
                    } else if (result.has(commandOptions.changeClusterState)) {
                        CommandClusterChangeState.apply(result, settings);
                    } else if (result.has(commandOptions.changeClusterSettings)) {
                        CommandSetMasterMember.apply(result, reader, hosts, settings);
                    } else if (result.has(commandOptions.startManagementCenter)) {
                        CommandManagementCenterStart.apply(result, settings);
                    } else if(result.has(commandOptions.nsGet)) {
                    	CommandNSGet.apply();
                    } else if(result.has(CommandOptions.nsReset)) {
                    	CommandNSReset.apply();
                	} else if(result.has(commandOptions.nsSet)){
                		CommandNSSet.apply(result);
                    } else if(result.has(commandOptions.mapPut)) {
                    	CommandMapPut.apply(result);
                    } else if(result.has(commandOptions.mapSize)) {
                    	CommandMapSize.apply();
                    } else if(result.has(commandOptions.mapGet)) {
                    	CommandMapGet.apply(result);
                    } else if(result.has(commandOptions.mapGetAll)) {
                    	CommandMapGetAll.apply();
                    } else if(result.has(commandOptions.mapRemove)) {
                    	CommandMapRemove.apply(result);
                    } else if(result.has(commandOptions.mapUnlock)) {
                    	CommandMapUnlock.apply(result);
                    } else if(result.has(commandOptions.mapKeys)) {
                    	CommandMapKeys.apply();
                    } else if(result.has(commandOptions.mapValues)) {
                    	CommandMapKeys.apply();
                    } else if(result.has(commandOptions.mapDestroy)) {
                    	CommandMapDestroy.apply();
                    } else if(result.has(commandOptions.mapClear)) {
                    	CommandMapClear.apply();
                    } else if(result.has(commandOptions.mapLock)) {
                    	CommandMapLock.apply(result);
                    } else if(result.has(commandOptions.mapTryLock)) {
                    	CommandMapTryLock.apply(result);
                    } else if(result.has(CommandOptions.listAdd)) {
                    	CommandListAdd.apply(result);
                	} else if(result.has(CommandOptions.listClear)) {
                    	CommandListClear.apply();
                	} else if(result.has(CommandOptions.listContains)) {
                    	CommandListContains.apply(result);
                	} else if(result.has(CommandOptions.listGet)) {
                    	CommandListGet.apply(result);
                	} else if(result.has(CommandOptions.listGetAll)) {
                    	CommandListGetAll.apply();
                	} else if(result.has(CommandOptions.listGetMany)) {
                    	CommandListGetMany.apply(result);
                	} else if(result.has(CommandOptions.listRemove)) {
                    	CommandListRemove.apply(result);
                	} else if(result.has(CommandOptions.listSet)) {
                    	CommandListSet.apply(result);
                	} else if(result.has(CommandOptions.listSize)) {
                		CommandListSize.apply();
                	} else if(result.has(CommandOptions.queueClear)) {
                    	CommandQueueClear.apply();
                	} else if(result.has(CommandOptions.queueOffer)) {
                		CommandQueueOffer.apply(result);
                	} else if(result.has(CommandOptions.queueOfferMany)) {
                		CommandQueueOfferMany.apply(result);
                	} else if(result.has(CommandOptions.queuePeek)) {
                		CommandQueuePeek.apply();
                	} else if(result.has(CommandOptions.queuePollMany)) {
                		CommandQueuePollMany.apply(result);
                	} else if(result.has(CommandOptions.queueSize)) {
                		CommandQueueSize.apply();
                	} else if(result.has(CommandOptions.queuePoll)) {
                		CommandQueuePoll.apply();
                	} else if(result.has(commandOptions.setAdd)) {
                    	CommandSetAdd.apply(result);
                    } else if(result.has(commandOptions.setClear)) {
                    	CommandSetClear.apply();
                    } else if(result.has(commandOptions.setGetAll)) {
                    	CommandSetGetAll.apply();
                    } else if(result.has(commandOptions.setRemove)) {
                    	CommandSetRemove.apply(result);
                    }  else if(result.has(commandOptions.setSize)) {
                    	CommandSetSize.apply();
                    } else if (result.has(commandOptions.exit)) {
                        CommandExitProgram.apply();
                        open = false;
                    } else if (result.has(commandOptions.memAll)) {
                    	CommandMemoryAll.apply(result);
                    } else if (result.has(commandOptions.memList)) {
                    	CommandMemoryList.apply(result);
                    } else if (result.has(commandOptions.memMap)) {
                    	CommandMemoryMap.apply(result);
                    } else if (result.has(commandOptions.memSet)) {
                    	CommandMemorySet.apply(result);
                    } else if (result.has(commandOptions.memQueue)) {
                    	CommandMemoryQueue.apply(result);
                    } else if (!input.equals("")) {
                        System.out.println("Command not valid. Please type help to see valid command options");
                    }
                }
            } catch (Exception e) {
            	instance.shutdown();
            }
        }
    }

    private static void readMemberInfoFile() throws Exception {

        for (HostSettings host : hosts) {
            DefaultSessionFactory defaultSessionFactory = new DefaultSessionFactory(
                    host.userName, host.hostIp, host.sshPort);
            try {
                defaultSessionFactory.setIdentityFromPrivateKey(host.identityPath);
            } catch (JSchException e) {
                System.out.println("error");
            }
            sessions.put(host.hostName, defaultSessionFactory);
            try {
                ScpFile to = new ScpFile(defaultSessionFactory,
                        "/home/ubuntu/hazelcast/members.txt");
                File file = File.createTempFile("members", ".tmp");
                to.copyTo(file);
                files.put(host.hostName, file);
                for (String str : FileUtils.readLines(file)) {
                    CLI.members.put(str.split(" ")[1], new AbstractMap.SimpleEntry(host.hostName, str.split(" ")[0]));
                    if (CLI.firstMember.get(settings.clusterName) == null) {
                        settings.user = host.userName;
                        settings.hostIp = host.hostIp;
                        settings.identityPath = host.identityPath;
                        settings.port = host.sshPort;
                        settings.memberPort = str.split(" ")[0];
                        CLI.firstMember.put(settings.clusterName, str.split(" ")[1]);
                    }
                }
            } catch (Exception e) {
                if (files.get(host.hostName) == null) {
                    files.put(host.hostName, File.createTempFile("members", ".tmp"));
                }
            }
        }
    }

    private static void addHosts(Set<HostSettings> hosts) throws Exception {
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
            HostSettings host = new HostSettings(key, userName, hostIp, remotePath, identityPath);

            System.out.println("Connection settings set for " + host.userName + "@" + host.hostIp);
            String message = SshExecutor.exec(host.userName, host.hostIp, 22, "", false, host.identityPath, false);
            if ((message == null) || (!message.equals("exception"))) {
                System.out.println("Host " + host.hostName + " is added.");
                hosts.add(host);
            } else {
                System.out.println("Could not connect to the hosts.");
                System.out.println("Please try to add a hosts again.");
            }
        }

    }

}

