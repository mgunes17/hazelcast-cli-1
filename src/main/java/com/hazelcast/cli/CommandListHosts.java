package com.hazelcast.cli;

import java.util.Set;

public class CommandListHosts {

    public static void apply (Set<HostSettings> machines) throws Exception {

            for (HostSettings machine: machines)
                System.out.println(
                        "\n" +
                        "Machine Name   :   " + machine.hostName + "\n" +
                        "User Name      :   " + machine.userName + "\n" +
                        "Host Ip        :   " + machine.hostIp + "\n" +
                        "Remote Path    :   " + machine.remotePath + "\n" +
                        "Identity Path  :   " + machine.identityPath
                );
        }

}
