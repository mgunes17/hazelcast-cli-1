package com.hazelcast.cli;

import java.util.Set;

public class CommandListMachines {

    public static void apply (Set<MachineSettings> machines) throws Exception {

            for (MachineSettings machine: machines)
                System.out.println(
                        "\n" +
                        "Machine Name   :   " + machine.machineName + "\n" +
                        "User Name      :   " + machine.userName + "\n" +
                        "Host Ip        :   " + machine.hostIp + "\n" +
                        "Remote Path    :   " + machine.remotePath + "\n" +
                        "Identity Path  :   " + machine.identityPath
                );
        }

}
