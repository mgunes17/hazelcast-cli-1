package com.hazelcast.cli;

import joptsimple.OptionSet;

import static com.hazelcast.cli.CLI.firstMember;
import static com.hazelcast.cli.CLI.hosts;
import static com.hazelcast.cli.CLI.members;

/**
 * Created by bilal on 08/07/16.
 */
public class CommandCliInfo {
    public static void apply(OptionSet result, ClusterSettings settings) {
        System.out.println("Connected Host Number : " + hosts.size());
        System.out.println("Connected Member Size : " + members.size());
        System.out.println("Credentials           : " + settings.clusterName + "/" + settings.password);
        System.out.println("Master Member Tag     : " + firstMember.get(settings.clusterName));
    }
}
