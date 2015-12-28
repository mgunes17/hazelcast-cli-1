package com.hazelcast.cli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by emrah on 12/06/15.
 */
public class CLI {

    public static void main(String[] args) throws Exception {

        //Setting options for the command
        OptionParser optionParser = new OptionParser();
        OptionSpec install = optionParser.accepts("install", "Install hz");
        OptionSpec start = optionParser.accepts("start", "Start hz");
        OptionSpec startMC = optionParser.accepts("startMC", "Start hz mancenter");
        OptionSpec hostName = optionParser.accepts("hostname").withRequiredArg().ofType(String.class).required();
        OptionSpec version = optionParser.accepts("version").withRequiredArg().ofType(String.class);
        OptionSpec optionClusterName = optionParser.accepts("clustername").withRequiredArg().ofType(String.class);
        OptionSpec optionNodeName = optionParser.accepts("nodename").withRequiredArg().ofType(String.class);
        OptionSpec optionConfigFile = optionParser.accepts("configfile").withRequiredArg().ofType(String.class);

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

                String move = commandBuilder.move("hazelcast-" + strVersion, "hazelcast");
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
            String cmd = commandBuilder.upload(user, hostIp, nodeName, configFile);
            System.out.println("Uploading config file...");
            Runtime.getRuntime().exec(cmd);
            System.out.println("Upload completed.");
            System.out.println("Starting instance...");
            String startCmd = commandBuilder.start("~/hazelcast-" + nodeName + ".xml");

            //returns the pid, but how?
            String pid = SshExecutor.exec(user, hostIp, port, startCmd, true);
            System.out.println("Instance started : " + pid);

        } else if(result.has(startMC)) {
            //how does commandBuilder.startMC() know the full path?
            SshExecutor.exec(user, hostIp, port, commandBuilder.startMC(), false);
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
