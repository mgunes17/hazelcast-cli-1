package com.hazelcast.cli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.util.Arrays;

/**
 * Created by mefeakengin on 1/19/16.
 */
public class CommandOptions {

    static OptionParser optionParser = new OptionParser();
    static OptionSpec help = optionParser.acceptsAll(Arrays.asList("help", "h"));
    static OptionSpec exit = optionParser.acceptsAll(Arrays.asList("exit", "e"));
    static OptionSpec addMachine = optionParser.acceptsAll(Arrays.asList("add-machine")).withOptionalArg().ofType(String.class);
    static OptionSpec removeMachine = optionParser.acceptsAll(Arrays.asList("remove-machine")).withOptionalArg().ofType(String.class);
    static OptionSpec listMachines = optionParser.acceptsAll(Arrays.asList("list-machines"));

    static OptionSpec install = optionParser.acceptsAll(Arrays.asList("install", "i")).withOptionalArg().ofType(String.class);
    static OptionSpec startMember = optionParser.acceptsAll(Arrays.asList("s", "start-member")).withOptionalArg().ofType(String.class);
    static OptionSpec startManagementCenter = optionParser.acceptsAll(Arrays.asList("start-mc", "management-center"));
    static OptionSpec version = optionParser.acceptsAll(Arrays.asList("v", "version")).withRequiredArg().ofType(String.class);

    //Options of operations
    static OptionSpec optionClusterName = optionParser.acceptsAll(Arrays.asList("c", "cluster-name")).withRequiredArg().ofType(String.class);
    static OptionSpec optionNodeName = optionParser.acceptsAll(Arrays.asList("n", "node-name")).withRequiredArg().ofType(String.class);
    static OptionSpec optionConfigFile = optionParser.acceptsAll(Arrays.asList("C", "config-file")).withRequiredArg().ofType(String.class);
    static OptionSpec optionGroupName = optionParser.acceptsAll(Arrays.asList("g", "group-name")).withRequiredArg().ofType(String.class);
    static OptionSpec optionPassword = optionParser.acceptsAll(Arrays.asList("P", "password")).withRequiredArg().ofType(String.class);
    static OptionSpec optionClusterPort = optionParser.acceptsAll(Arrays.asList("p", "port")).withRequiredArg().ofType(String.class);

    //Options for connection

    static OptionSpec optionMachineName = optionParser.acceptsAll(Arrays.asList("machine-name")).withRequiredArg().ofType(String.class);
    static OptionSpec optionUserName = optionParser.acceptsAll(Arrays.asList("user-name")).withRequiredArg().ofType(String.class);
    static OptionSpec optionIp = optionParser.acceptsAll(Arrays.asList("ip")).withRequiredArg().ofType(String.class);
    static OptionSpec optionRemotePath = optionParser.acceptsAll(Arrays.asList("remote-path")).withRequiredArg().ofType(String.class);
    static OptionSpec optionIdentityPath = optionParser.acceptsAll(Arrays.asList("identity-path")).withRequiredArg().ofType(String.class);
    static OptionSpec optionLocalPath = optionParser.acceptsAll(Arrays.asList("local-path")).withRequiredArg().ofType(String.class);

    //Options for cluster operations
    static OptionSpec shutdownCluster = optionParser.acceptsAll(Arrays.asList("shutdown", "S"));
    static OptionSpec killMember = optionParser.acceptsAll(Arrays.asList("k", "kill-member")).withRequiredArg().ofType(String.class);
    static OptionSpec listMember = optionParser.acceptsAll(Arrays.asList("l", "list-member"));
    static OptionSpec getClusterState = optionParser.acceptsAll(Arrays.asList("cluster-state"));
    static OptionSpec changeClusterState = optionParser.acceptsAll(Arrays.asList("cluster-change-state")).withRequiredArg().ofType(String.class);
    static OptionSpec changeClusterSettings = optionParser.acceptsAll(Arrays.asList("cluster-change-settings")).withRequiredArg().ofType(String.class);
    static OptionSpec clusterConnect = optionParser.acceptsAll(Arrays.asList("cluster-connect"));
    static OptionSpec clusterDisconnect = optionParser.acceptsAll(Arrays.asList("cluster-disconnect"));

    public OptionSet parse(String input) throws Exception {

        String[] inputArray = input.trim().split("[ \t]+");
        try {
            return this.optionParser.parse(inputArray);
        } catch (Exception e) {
            //TODO: Check if works/
            return this.parse("");
        }
    }

}
