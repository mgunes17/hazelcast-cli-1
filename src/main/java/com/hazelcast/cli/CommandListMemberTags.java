package com.hazelcast.cli;

import java.util.AbstractMap;

import static com.hazelcast.cli.CLI.members;

public class CommandListMemberTags {

    public static void apply() throws Exception {

        String formatStr = "%-20s %-15s %-15s%n";
        System.out.printf(formatStr, "tag", "host", "port");
        for (String tag : members.keySet()) {
            AbstractMap.SimpleEntry value = members.get(tag);
            System.out.printf(formatStr, tag, value.getKey(), value.getValue());
        }
    }

}
