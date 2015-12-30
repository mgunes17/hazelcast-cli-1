package com.hazelcast.cli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

/**
 * Created by emrah on 12/06/15.
 */
public class CLI {

    public static void main(String[] args) throws Exception {


        OptionParser optionParser = new OptionParser();

        //Setting operations for the command
        OptionSpec install = optionParser.accepts("install", "Install hz");
        OptionSpec start = optionParser.accepts("start", "Start hz");
        OptionSpec startMC = optionParser.accepts("startMC", "Start hz mancenter");
        OptionSpec hostName = optionParser.accepts("hostname").withRequiredArg().ofType(String.class).required();
        OptionSpec version = optionParser.accepts("version").withRequiredArg().ofType(String.class);

        //Options of operations
        OptionSpec optionClusterName = optionParser.accepts("clustername").withRequiredArg().ofType(String.class);
        OptionSpec optionNodeName = optionParser.accepts("nodename").withRequiredArg().ofType(String.class);
        OptionSpec optionConfigFile = optionParser.accepts("configfile").withRequiredArg().ofType(String.class);
        OptionSpec optionGroupName = optionParser.accepts("g", "groupname").withRequiredArg().ofType(String.class);
        OptionSpec optionPassword = optionParser.accepts("P", "password").withRequiredArg().ofType(String.class);
        OptionSpec optionPort = optionParser.accepts("p", "port").withRequiredArg().ofType(String.class);

        //Options for cluster operations
        OptionSpec shutdown = optionParser.accepts("shutdown");

        OptionSet result = optionParser.parse(args);

        //Building the user and other properties
        System.out.println("hostName: " + result.valueOf(hostName));
        String host = (String) result.valueOf(hostName);
        String userHome = System.getProperty("user.home");
        Properties properties = getProperties(userHome);
        String user = properties.getProperty(host + ".user");
        String hostIp = properties.getProperty(host + ".ip");
        int port = Integer.parseInt(properties.getProperty(host + ".port"));

        CommandBuilder commandBuilder = new CommandBuilder();

        //executing the command
        if (result.has(install)) {

            //Localhost
            if((host.equals("localhost"))){
                System.out.println("Working at localhost");
                if (!result.has(version)) {
                    System.out.println("--version required");
                    System.exit(-1);
                }
                String strVersion = (String) result.valueOf(version);

                String installCommand = commandBuilder.wget(strVersion);
                System.out.println("downloadCommand " + installCommand);
                System.out.println("Download started...");
                String installOutput = LocalExecutor.exec(installCommand, false);
                System.out.println("installOutput: " + installOutput);

                System.out.println("Extracting...");
                String extractCommand = commandBuilder.extract();
                String extractOutput = LocalExecutor.exec(extractCommand, false);
                System.out.println("extractOutput: " + extractOutput);

                String move = commandBuilder.move("hazelcast-" + strVersion, "hazelcast-deneme");
                LocalExecutor.exec(move, false);
                System.out.println("Installation completed...");
            }
            //Remotehost
            else {
                if (!result.has(version)) {
                    System.out.println("--version required");
                    System.exit(-1);
                }
                String strVersion = (String) result.valueOf(version);
                String command = commandBuilder.wget(strVersion);
                System.out.println("downloadCommand " + command);
                System.out.println("Download started...");
                String output = SshExecutor.exec(user, hostIp,
                        port, command, false);
                System.out.println("Extracting...");
                String extractCommand = commandBuilder.extract();
                SshExecutor.exec(user, hostIp, port, extractCommand, false);

                String move = commandBuilder.move("hazelcast-" + strVersion, "hazelcast");
                SshExecutor.exec(user, hostIp, port, move, false);
                System.out.println("Installation completed...");
            }

        } else if (result.has(start)) {
            String clusterName = (String) result.valueOf(optionClusterName);
            String nodeName = (String) result.valueOf(optionNodeName);
            String configFile = (String) result.valueOf(optionConfigFile);
            System.out.println("Configfile: " + configFile);
            String cmd = commandBuilder.upload(user, hostIp, nodeName, configFile);
            System.out.println("Uploading config file... " + cmd);
            Runtime.getRuntime().exec(cmd);
//            FileUtils.copyFileToDirectory(new File("/Users/mefeakengin/hazelcast/hazelcast.xml"), new File("ubuntu@54.84.254.202:/home/ubuntu/hazelcast-altunizade.xml"));
            System.out.println("Upload completed.");
            System.out.println("Starting instance...");
            String startCmd = commandBuilder.start("/home/ubuntu" + "/hazelcast-" + nodeName + ".xml");

            //TODO: returns the pid, but how?
            //TODO: can I assume that java is already installed to Amazon EC2
            String pid = SshExecutor.exec(user, hostIp, port, startCmd, true);
            System.out.println("Instance started : " + pid);

        } else if(result.has(startMC)) {
            //how does commandBuilder.startMC() know the full path?
            SshExecutor.exec(user, hostIp, port, commandBuilder.startMC(), false);

        } else if (result.has(shutdown)) {
            String groupName;
            if(result.has(optionGroupName)) {
                groupName = (String) result.valueOf(optionGroupName);
            } else {
                groupName = "dev";
                System.out.println("Group name is not specified, default group name is set to: " + groupName);
            }

            String password;
            if(result.has(optionGroupName)) {
                password = (String) result.valueOf(optionPassword);
            } else {
                password = "dev-pass";
                System.out.println("Group name is not specified, default password is set to: " + password);
            }

            String clusterPort;
            if(result.has(optionGroupName)) {
                clusterPort = (String) result.valueOf(optionPassword);
            } else {
                clusterPort = "5701";
                System.out.println("Group name is not specified, default clusterPort is set to: " + clusterPort);
            }

            String shutdownCmd = commandBuilder.shutdown(hostIp, clusterPort, groupName, password);

            SshExecutor.exec(user, hostIp, port, shutdownCmd, false);
        }
    }

    private static Properties getProperties(String userHome) {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "/hazelcast/hazelcast-cli/src/main/resources/cli.properties";
//            input = CLI.class.getClassLoader().getResourceAsStream(filename);
            //load a properties file from class path, inside static method

            prop.load(new InputStreamReader(new FileInputStream(userHome+filename)));
            return prop;

        } catch (IOException ex) {
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
