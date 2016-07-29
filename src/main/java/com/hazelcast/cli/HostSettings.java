package com.hazelcast.cli;

import joptsimple.OptionSet;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostSettings {

    public final String hostName;
    public final String userName;
    public final String hostIp;
    public final int sshPort;
    public final String remotePath;
    public final String identityPath;
    private static Logger logger = LoggerFactory.getLogger(HostSettings.class);

    public HostSettings(String hostName, String userName, String hostIp, String remotePath, String identityPath) {
    	logger.trace("Host is initializing");
        this.hostName = hostName;
        this.userName = userName;
        this.hostIp = hostIp;
        this.sshPort = 22;
        this.remotePath = remotePath;
        this.identityPath = identityPath;

    }

    public static HostSettings getMachine(OptionSet result, Set<HostSettings> machines, String machineName) {
        HostSettings machine = null;

        if(machineName == null) {
        	logger.trace("machine name is null");
            System.out.println("No machine name is given.");
        }

        boolean machineExists = false;
        for (HostSettings currentMachine : machines) {
            if(currentMachine.hostName.equals(machineName)) {
                machine = currentMachine;
                machineExists = true;
                logger.trace("Machine name is exist");
                break;
            }
        }
        if (!machineExists) {
            if(machineName != null) {
            	logger.trace("Machine with the name " + machineName + " does not exist.");
                System.out.println("Machine with the name " + machineName + " does not exist.");
            } else {
            	logger.trace("Machine name is invalid");
                System.out.println("Please enter a valid machine name.");
            }
            return null;
        } else {
            return machine;
        }
    }

}
