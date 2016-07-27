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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.jcraft.jsch.JSchException;
import com.pastdev.jsch.DefaultSessionFactory;
import com.pastdev.jsch.scp.ScpFile;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class CLI {

    public static ConsoleReader reader;
    public static Map<String, String> firstMember = new HashMap<String, String>();
    public static Map<String, AbstractMap.SimpleEntry<String, String>> members = new HashMap<String, AbstractMap.SimpleEntry<String, String>>();
    public static Set<HostSettings> hosts = new HashSet<HostSettings>();
    public static HashMap<String, File> files = new HashMap<String, File>();
    public static HashMap<String, DefaultSessionFactory> sessions = new HashMap<String, DefaultSessionFactory>();
    public static ClusterSettings settings;
    public static String nameSpace;
	public static String[] command;
	private static Logger logger = LoggerFactory.getLogger(CLI.class);
	public static  OptionSet result;
	private HazelcastInstance instance;

    public static void main(String[] args) throws Exception {

        reader = new ConsoleReader();
        System.out.println((new HazelcastArt()).art);
        System.out.println(
                "Welcome to Hazelcast command line interface.\n" +
                        "Type help to see command options.");
        mainConsole();
    }

    private static void mainConsole() throws Exception {
    	CLI cli = new CLI();
    	Config config = new Config();
		PropertiesFile po = new PropertiesFile("cli.properties");
		ClassLoader classLoader = po.loadClasses();
		
		config.setClassLoader(classLoader);
		cli.instance = Hazelcast.newHazelcastInstance(config);
		
        Boolean open = true;
        CommandOptions commandOptions = new CommandOptions();
        settings = new ClusterSettings();
        addHosts(hosts);
        readMemberInfoFile();
        while (open) {

            try {
                String input = reader.readLine("hz " + settings.clusterName + "-> ");
                
                result = commandOptions.parse(input);


                if (CommandOptions.commandList.containsKey(input)) {
                	CommandOptions.commandList.get(input).run();
                }
                else {
                	Command command = new Command(cli.instance);
                	command.process(input);
                }
            }
           catch (Exception e) {
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

