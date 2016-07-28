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
                        
                        "Namespace Operations:\n" +
                        "ns-set [NAME]                     : Assignes a name to namespace.\n" +
                        "ns-get                            : Prints the namespace.\n" +
                        "ns-reset                          : Assignes null to namespace\n" +
                        "\n\n" +
                        
                        "Collection Operations:\n\n" +
                        "These operations come true for name that defined as namespace\n" +
                        "--- List Operations ---\n" +
                        "list-add [VALUE]                  : Adds the value to the list.\n" +
                        "list-set [INDEX] [VALUE]          : Sets the value at given the index.\n" +
                        "list-get [INDEX]                  : Prints the value at given the index.\n" +
                        "list-getall                       : Prints all values at the list.\n" +
                        "list-getmany [INDEX, INDEX, ..]   : Prints values at given indexes.\n" +
                        "list-size                         : Prints size of the list\n" +
                        "list-contains [VALUE]             : Prints whether value is at the list or not.\n" +
                        "list-remove [INDEX]               : Removes value at list from given index from the list.\n" +
                        "list-clear                        : Removes all values at list.\n" +
                        "\n" + 
                        "--- Queue Operations --- \n" +
                        "queue-clear                       : Removes all values from the queue.\n" +
                        "queue-offer [VALUE]               : Adds given value to the queue.\n" +
                        "queue-offermany [VALUE, VALUE, .. : Adds gven values to the queue.\n" +
                        "queue-peek                        : Peeks queue.\n" +
                        "queue-size                        : Prints size of the queue.\n" +
                        "queue-poll                        : Dequeues value at head of the queue.\n" +
                        "queue-pollmany [NUMBER]           : Dequeus given number values from the queue.\n" +
                        "\n" +
                        "--- Set Operations --- \n" +
                        "set-add [VALUE]                   : Adds given value to the set.\n" +
                        "set-clear                         : Removes all values at the set.\n" +
                        "set-getall                        : Prints all values at the set \n" +
                        "set-remove [VALUE]                : Removes given value from the set\n" +
                        "set-size                          : Prints size of the set\n" +
                        "\n" +
                        "--- Map Operations --- \n" +
                        "map-put [KEY][VALUE]              : Puts an key-value pair to the map.\n" +
                        "map-get [KEY]                     : Prints value of given key.\n" +
                        "map-size                          : Prints size of the map.\n" +
                        "map-getall                        : Prints all key-value pairs.\n" +
                        "map-remove [KEY]                  : Removes the given key and its value.\n" +
                        "map-keys                          : Prints all keys.\n" +
                        "map-values                        : Prints all values.\n" +
                        "map-destroy                       : Destroys the map.\n" +
                        "map-lock [KEY]                    : Locks the given key.\n" +
                        "map-trylock [KEY]                 : Tries to lock the given key.\n" +
                        "map-unlock [KEY]                  : Unlocks the given key.\n" +
                        "map-clear                         : Clears the map.\n" +
                        "\n\n" +
                        
                        "Memory Operations:\n" +
                        "mem-all [SIZE UNIT]               : Prints all collections and their memory sizes.\n" +
                        "mem-map [SIZE UNIT]               : Prints all maps and their memory sizes\n" +
                        "mem-list [SIZE UNIT]              : Prints all lists and their memory sizes\n" +
                        "mem-set [SIZE UNIT]               : Prints all sets and their memory sizes\n" +
                        "mem-queue [SIZE UNIT]             : Prints all queues and their memory sizes\n" +
                        
                        " --- Example CommandPipe --- \n" +
                        "set-credentials dev dev-pass\n" +
                        "start-member host1 -t firstmember\n" +
                        "list-members\n" +
                        "kill-member firstmember\n"
        );
    }

}
