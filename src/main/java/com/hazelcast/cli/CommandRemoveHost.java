package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandRemoveHost {
	private static Logger logger = LoggerFactory.getLogger(CommandRemoveHost.class);
	
    public static void apply (OptionSet result, Set<HostSettings> machines) throws Exception {

        boolean machineRemoved = false;
        try {
            String machineName = (String) result.valueOf("remove-machine");

            for (HostSettings machine : machines) {
                if(machine.hostName.equals(machineName)) {
                    machines.remove(machine);
                    logger.trace("Machine has been removed");
                    machineRemoved = true;
                    break;
                }
            }

            if (!machineRemoved) {
                if (machineName == null) {
                	logger.trace("Machine name is invalid");
                    System.out.println(
                            "Please enter a valid machine name.");
                } else {
                	logger.trace("Machine with the name '" + machineName + "' does not exist.");
                    System.out.println(
                            "Machine with the name '" + machineName + "' does not exist.");
                }
            } else {
                System.out.println(
                        "Machine '" + machineName + "' is removed.");
            }

        } catch (Exception e) {
            //TODO: Can there be other exceptions?
        	logger.warn("Invalid machine name", e);
            System.out.println("Please enter a valid machine name from the list of hosts.");
        }

    }

}
