package com.hazelcast.cli;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandListHosts {
	private static Logger logger = LoggerFactory.getLogger(CommandListHosts.class);
	
    public static void apply (Set<HostSettings> machines) throws Exception {
    		logger.trace("Host Settings");
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
