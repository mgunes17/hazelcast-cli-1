package com.hazelcast.cli;

/**
 * Created by mefeakengin on 1/19/16.
 */
public class CommandHelp {

    public static void apply() {
        System.out.println(
                "\n" +
                        "This command line interface is created for managing and provisioning of hazelcast clusters and members.\n" +
                        "\n" +
                        "Main Operations \n" +
                        "remove-machine                    : Removes a machine from the machines list.\n" +
                        "list-machines                     : Lists added machines.\n" +
                        "exit, --e                         : Exits from the program.\n" +
                        "help, --h                         : Lists the valid options.\n" +
                        "install, --i                      : Downloads and extracts hazelcast with the given version. Version is required.\n" +
                        "start-member [HOST] -t [TAG]      : Starts a member from a hazelcast configuration .xml file given with the local file path.\n" +
                        "                                     HOST name, MEMBER TAG are required.\n" +
                        "\n" +
                        "Cluster Operations:\n" +
                        "cluster-change-state [STATE]      : Changes the state of the cluster. Change parameter should be specified as one of active, passive or frozen.\n" +
                        "create-cluster                    : Connects to a cluster.\n" +
                        "cluster-disconnect                : Disconnects from the cluster.\n" +
                        "cluster-shutdown, --S             : Shuts down the cluster. The command should be sent to any node in the cluster.\n" +
                        "cluster-state                     : Gets the state of the cluster. Cluster state is active, passive or frozen.\n" +

                        "kill-member [TAG]                 : Kills the node according to given tag. Cluster connection and tag of the member to kill are required.\n" +
                        "list-member, --l                  : Lists the node details in a cluster. Cluster connection is required.\n" +
                        "-managament-center, --start-mc     : Starts the managament center with the url given in the .xml file. Cluster connection is required.\n" +
                        "\n" +
                        "Operation Options:\n" +
                        "-config-file, --C                 : Specifies the configuration .xml file for starting a node.\n" +
                        "-hazelcast-version, --hv          : Specifies the version of hazelcast to be downloaded. Example: --version 3.5.4 \n" +
                        "-tag                              : Specifies the node name.\n" +
                        "\n" +
                        " --- Example CommandPipe --- \n" +
                        "create-cluster\n" +
                        "start-member host1 -t firstmember\n" +
                        "list-member\n" +
                        "kill-member firstmember\n"
        );
    }

}
