package com.hazelcast.cli;

import java.util.AbstractMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.hazelcast.cli.CLI.members;

public class CommandListMemberTags {
	private static Logger logger = LoggerFactory.getLogger(CommandListMemberTags.class);
	
    public static void apply() throws Exception {
    	logger.trace("List member tags");
        String formatStr = "%-20s %-15s %-15s%n";
        System.out.printf(formatStr, "tag", "host", "port");
        for (String tag : members.keySet()) {
        	logger.trace("Member");
            AbstractMap.SimpleEntry value = members.get(tag);
            System.out.printf(formatStr, tag, value.getKey(), value.getValue());
        }
    }

}
