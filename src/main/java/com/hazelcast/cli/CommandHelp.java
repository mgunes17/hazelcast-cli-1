package com.hazelcast.cli;

public class CommandHelp {

    public static void apply() {
        System.out.println(
                "\n" +
                        "This command line interface is created for managing and provisioning of hazelcast clusters and members.\n" +
                        "\n" +
                        "Configuration/Provision Commands \n" +
                        "list-hosts                        : Lists added hosts.\n" +
                        "install [HOST] [VERSION]          : Downloads and extracts hazelcast with the given version to given host. Version and host are required.\n" +
                        "set-credentials [GROUP] [PASS]    : Lists the valid options.\n" +
                        "set-master-member [MEMBER TAG]    : Configures master member. CLI sends cluster wide operations to a master member.\n" +
                        "exit                              : Exits from the program.\n" +
                        "help                              : Lists the valid options.\n" +
                        "\n" +

                        "Cluster Operations:\n" +
                        "cluster-change-state [STATE]      : Changes the state of the cluster. Change parameter should be specified as one of active, passive or frozen.\n" +
                        "cluster-disconnect                : Disconnects from the cluster.\n" +
                        "cluster-shutdown                  : Shuts down the cluster. The command should be sent to any node in the cluster.\n" +
                        "cluster-state                     : Gets the state of the cluster. Cluster state is active, passive or frozen.\n" +

                        "start-member [HOST] -t [TAG]      : Starts a member from a hazelcast configuration .xml file given with the local file path.\n" +
                        "                                     HOST name, MEMBER TAG are required.\n" +
                        "kill-member [TAG]                 : Kills the node according to given tag. Cluster connection and tag of the member to kill are required.\n" +
                        "list-members                      : Lists the node details in a cluster. Cluster connection is required.\n" +
                        "start-managament-center           : Starts the managament center with the url given in the .xml file. Cluster connection is required.\n" +
                        "\n" +
                        " --- Example CommandPipe --- \n" +
                        "set-credentials dev dev-pass\n" +
                        "start-member host1 -t firstmember\n" +
                        "list-members\n" +
                        "kill-member firstmember\n"
        );
    }

}
