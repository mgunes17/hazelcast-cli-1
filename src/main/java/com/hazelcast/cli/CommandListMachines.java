package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.List;
import java.util.Set;

/**
 * Created by mefeakengin on 1/25/16.
 */
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
