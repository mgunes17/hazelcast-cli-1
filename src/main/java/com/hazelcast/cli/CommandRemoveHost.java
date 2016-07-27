package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;

public class CommandRemoveHost {

    public static void apply (OptionSet result, Set<HostSettings> machines) throws Exception {

        boolean machineRemoved = false;
        try {
            String machineName = (String) result.valueOf("remove-machine");

            for (HostSettings machine : machines) {
                if(machine.hostName.equals(machineName)) {
                    machines.remove(machine);
                    machineRemoved = true;
                    break;
                }
            }

            if (!machineRemoved) {
                if (machineName == null) {
                    System.out.println(
                            "Please enter a valid machine name.");
                } else {
                    System.out.println(
                            "Machine with the name '" + machineName + "' does not exist.");
                }
            } else {
                System.out.println(
                        "Machine '" + machineName + "' is removed.");
            }

        } catch (Exception e) {
            //TODO: Can there be other exceptions?
            System.out.println("Please enter a valid machine name from the list of hosts.");
        }

    }

}
