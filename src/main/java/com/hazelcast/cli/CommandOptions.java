package com.hazelcast.cli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandOptions {
	private static Logger logger = LoggerFactory.getLogger(CommandOptions.class);
    static OptionParser optionParser = new OptionParser();
    static OptionSpec help = optionParser.acceptsAll(Arrays.asList("help", "h"));
    static OptionSpec exit = optionParser.acceptsAll(Arrays.asList("exit", "e"));
    static OptionSpec removeHost = optionParser.acceptsAll(Arrays.asList("remove-machine")).withOptionalArg().ofType(String.class);
    static OptionSpec listHosts = optionParser.acceptsAll(Arrays.asList("list-hosts"));

    static OptionSpec install = optionParser.acceptsAll(Arrays.asList("install", "i"));
    static OptionSpec startMember = optionParser.acceptsAll(Arrays.asList("S", "start-member")).withOptionalArg().ofType(String.class);
    static OptionSpec startManagementCenter = optionParser.acceptsAll(Arrays.asList("start-mc", "management-center"));
    static OptionSpec version = optionParser.acceptsAll(Arrays.asList("v", "version")).withRequiredArg().ofType(String.class);

    //Options of operations
    static OptionSpec optionNodeName = optionParser.acceptsAll(Arrays.asList("t", "tag")).withRequiredArg().ofType(String.class);
    static OptionSpec optionConfigFile = optionParser.acceptsAll(Arrays.asList("C", "config-file")).withRequiredArg().ofType(String.class);

    //Options for cluster operations
    static OptionSpec shutdownCluster = optionParser.acceptsAll(Arrays.asList("cluster-shutdown", "S"));
    static OptionSpec CliInfo = optionParser.acceptsAll(Arrays.asList("info", "i"));
    static OptionSpec killMember = optionParser.acceptsAll(Arrays.asList("k", "kill-member")).withOptionalArg().ofType(String.class);
    static OptionSpec listMember = optionParser.acceptsAll(Arrays.asList("l", "list-members"));
    static OptionSpec listMemberTags = optionParser.acceptsAll(Arrays.asList("l", "list-member-tags"));
    static OptionSpec getClusterState = optionParser.acceptsAll(Arrays.asList("cluster-state"));
    static OptionSpec changeClusterState = optionParser.acceptsAll(Arrays.asList("cluster-change-state")).withOptionalArg().ofType(String.class);
    static OptionSpec changeClusterSettings = optionParser.acceptsAll(Arrays.asList("set-master-member"));
    static OptionSpec setCredentials = optionParser.acceptsAll(Arrays.asList("set-credentials"));
    static OptionSpec forceStart = optionParser.acceptsAll(Arrays.asList("cluster-force-start"));
    static OptionSpec clusterDisconnect = optionParser.acceptsAll(Arrays.asList("cluster-disconnect"));
    
    //Options for namepace operations
    static OptionSpec nsGet = optionParser.acceptsAll(Arrays.asList("ns-get"));
    static OptionSpec nsSet = optionParser.acceptsAll(Arrays.asList("ns-set"));
    static OptionSpec nsReset = optionParser.acceptsAll(Arrays.asList("ns-reset"));
    
    //Options for map operations
    static OptionSpec mapPut = optionParser.acceptsAll(Arrays.asList("map-put"));
    static OptionSpec mapGet = optionParser.acceptsAll(Arrays.asList("map-get"));
    static OptionSpec mapSize = optionParser.acceptsAll(Arrays.asList("map-size"));
    static OptionSpec mapGetAll = optionParser.acceptsAll(Arrays.asList("map-getall"));
    static OptionSpec mapRemove = optionParser.acceptsAll(Arrays.asList("map-remove"));
    static OptionSpec mapKeys = optionParser.acceptsAll(Arrays.asList("map-keys"));
    static OptionSpec mapValues = optionParser.acceptsAll(Arrays.asList("map-values"));
    static OptionSpec mapDestroy = optionParser.acceptsAll(Arrays.asList("map-destroy"));
    static OptionSpec mapLock = optionParser.acceptsAll(Arrays.asList("map-lock"));
    static OptionSpec mapTryLock = optionParser.acceptsAll(Arrays.asList("map-trylock"));
    static OptionSpec mapUnlock = optionParser.acceptsAll(Arrays.asList("map-unlock"));
    static OptionSpec mapClear = optionParser.acceptsAll(Arrays.asList("map-clear"));
    
    //Options for list operations
    static OptionSpec listAdd = optionParser.acceptsAll(Arrays.asList("list-add"));
    static OptionSpec listSet = optionParser.acceptsAll(Arrays.asList("list-set"));
    static OptionSpec listGet = optionParser.acceptsAll(Arrays.asList("list-get"));
    static OptionSpec listGetAll = optionParser.acceptsAll(Arrays.asList("list-getall"));
    static OptionSpec listGetMany = optionParser.acceptsAll(Arrays.asList("list-getmany"));
    static OptionSpec listSize = optionParser.acceptsAll(Arrays.asList("list-size"));
    static OptionSpec listContains = optionParser.acceptsAll(Arrays.asList("list-contains"));
    static OptionSpec listRemove = optionParser.acceptsAll(Arrays.asList("list-remove"));
    static OptionSpec listClear = optionParser.acceptsAll(Arrays.asList("list-clear"));
    
    //Options for queue operations
    static OptionSpec queueClear = optionParser.acceptsAll(Arrays.asList("queue-clear"));
    static OptionSpec queueOffer = optionParser.acceptsAll(Arrays.asList("queue-offer"));
    static OptionSpec queueOfferMany = optionParser.acceptsAll(Arrays.asList("queue-offermany"));
    static OptionSpec queuePeek = optionParser.acceptsAll(Arrays.asList("queue-peek"));
    static OptionSpec queueSize = optionParser.acceptsAll(Arrays.asList("queue-size"));
    static OptionSpec queuePoll = optionParser.acceptsAll(Arrays.asList("queue-poll"));
    static OptionSpec queuePollMany = optionParser.acceptsAll(Arrays.asList("queue-pollmany"));
    
    //Options for set operations
    static OptionSpec setAdd = optionParser.acceptsAll(Arrays.asList("set-add"));
    static OptionSpec setClear = optionParser.acceptsAll(Arrays.asList("set-clear"));
    static OptionSpec setGetAll = optionParser.acceptsAll(Arrays.asList("set-getall"));
    static OptionSpec setRemove = optionParser.acceptsAll(Arrays.asList("set-remove"));
    static OptionSpec setSize = optionParser.acceptsAll(Arrays.asList("set-size"));
    
    //Options for memory operations
    static OptionSpec memAll = optionParser.acceptsAll(Arrays.asList("mem-all"));
    static OptionSpec memMap = optionParser.acceptsAll(Arrays.asList("mem-map"));
    static OptionSpec memList = optionParser.acceptsAll(Arrays.asList("mem-list"));
    static OptionSpec memSet = optionParser.acceptsAll(Arrays.asList("mem-set"));
    static OptionSpec memQueue= optionParser.acceptsAll(Arrays.asList("mem-queue"));
    
    public OptionSet parse(String input) throws Exception {

        String[] inputArray = input.trim().split("[ \t]+");
        try {
        	logger.info("Input is parsing");
            return this.optionParser.parse(inputArray);
        } catch (Exception e) {
            //TODO: Check if works/
        	logger.warn("Input parsing error",e);
            return this.parse("");
        }
    }

}
