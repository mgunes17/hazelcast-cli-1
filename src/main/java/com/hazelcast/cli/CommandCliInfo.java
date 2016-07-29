package com.hazelcast.cli;

import joptsimple.OptionSet;

import static com.hazelcast.cli.CLI.firstMember;
import static com.hazelcast.cli.CLI.hosts;
import static com.hazelcast.cli.CLI.members;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bilal on 08/07/16.
 */
public class CommandCliInfo {
	private static Logger logger = LoggerFactory.getLogger(CommandCliInfo.class);
	 
    public static void apply(OptionSet result, ClusterSettings settings) {
    	logger.trace("Printing host setttings");
        System.out.println("Connected Host Number : " + hosts.size());
        System.out.println("Connected Member Size : " + members.size());
        System.out.println("Credentials           : " + settings.clusterName + "/" + settings.password);
        System.out.println("Master Member Tag     : " + firstMember.get(settings.clusterName));
    }
}
