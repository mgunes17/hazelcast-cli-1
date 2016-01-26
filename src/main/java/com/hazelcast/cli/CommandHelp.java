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
            "--add-machine                      : Adds a machine for operation.. \n" +
            "--remove-machine                   : Removes a machine from the machines list.\n" +
            "--list-machines                    : Lists added machines.\n" +
            "--exit, --e                        : Exits from the program.\n" +
            "--help, --h                        : Lists the valid options.\n" +
            "--install, --i                     : Downloads and extracts hazelcast with the given version. Version is required.\n" +
            "--start-member, --s                : Starts a member from a hazelcast configuration .xml file given with the local file path.\n" +
            "                                     Cluster name, node name, config files are required.\n" +
            "\n" +
            "Cluster Operations:\n" +
            "--cluster-change-state             : Changes the state of the cluster. Change parameter should be specified as one of active, passive or frozen.\n" +
            "--cluster-connect                  : Connects to a cluster.\n" +
            "--cluster-disconnect               : Disconnects from the cluster.\n" +
            "--cluster-shutdown, --S            : Shuts down the cluster. The command should be sent to any node in the cluster.\n" +
            "--cluster-state                    : Gets the state of the cluster. Cluster state is active, passive or frozen.\n" +

            "--kill-member, --k                 : Kills the node at the specified host and port. Cluster connection and port of the member to kill are required.\n" +
            "--list-member, --l                 : Lists the node details in a cluster. Cluster connection is required.\n" +
            "--managament-center, --start-mc    : Starts the managament center with the url given in the .xml file. Cluster connection is required.\n" +
            "\n" +
            "Operation Options:\n" +
            "--cluster-name                     : Specifies the cluster name.\n" +
            "--config-file, --C                 : Specifies the configuration .xml file for starting a node.\n" +
            "--group-name, --g                  : Specifies the group name. Required for cluster operations. Default is dev.\n" +
            "--hazelcast-version, --hv          : Specifies the version of hazelcast to be downloaded. Example: --version 3.5.4 \n" +
            "--hostname, -H                     : Specifies the hostname.\n" +
            "--node-name                        : Specifies the node name.\n" +
            "--password, --P                    : Specifies the password for the cluster group. Required for cluster operations. Default is dev-pass.\n" +
            "--port, --p                        : Specifies the node port at the host IP. Required for cluster and node operations. Default is 5701.\n" +
            "\n" +
            " --- Example CommandOptions --- \n" +
            "--hostname ubuntu --start --cluster-name cluster --node-name node --configfile /Users/user/hazelcast/hazelcast.xml\n" +
            "--hostname ubuntu --shutdown\n" +
            "--hostname ubuntu --killNode --port 5703\n"
        );
    }

}
