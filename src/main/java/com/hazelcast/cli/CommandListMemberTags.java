package com.hazelcast.cli;

import java.util.AbstractMap;

import static com.hazelcast.cli.CLI.members;

public class CommandListMemberTags {

    public static void apply() throws Exception {

        System.out.println("tag\thost\tport");
        for (String tag : members.keySet()) {
            AbstractMap.SimpleEntry value = members.get(tag);
            System.out.println(tag + "\t" + value.getKey() + "\t" + value.getValue());
        }
    }

}
