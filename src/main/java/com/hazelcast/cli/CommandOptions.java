package com.hazelcast.cli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandOptions {

	static Map<String, Runnable> commandList = new HashMap<String, Runnable>();
	static OptionParser optionParser = new OptionParser();
	
    static OptionSpec forceStart = optionParser.acceptsAll(Arrays.asList("cluster-force-start"));
    static OptionSpec changeClusterState = optionParser.acceptsAll(Arrays.asList("cluster-change-state")).withOptionalArg().ofType(String.class);
    static OptionSpec killMember = optionParser.acceptsAll(Arrays.asList("k", "kill-member")).withOptionalArg().ofType(String.class);
    static OptionSpec optionNodeName = optionParser.acceptsAll(Arrays.asList("t", "tag")).withRequiredArg().ofType(String.class);
    static OptionSpec optionConfigFile = optionParser.acceptsAll(Arrays.asList("C", "config-file")).withRequiredArg().ofType(String.class);
    
	public CommandOptions() {
		try{
			commandList.put("help", () -> CommandHelp.apply());
			commandList.put("exit", () -> CommandExitProgram.apply());
			commandList.put("remove-machine", () -> CommandRemoveHost.apply(CLI.result, CLI.hosts));
			commandList.put("list-hosts", () -> CommandListHosts.apply(CLI.hosts));
			commandList.put("install", () -> CommandInstall.apply(CLI.result, CLI.hosts)); //TODO: Handle properties set for local/remote
			commandList.put("start-member", () -> CommandStartMember.apply(CLI.result, CLI.settings, CLI.hosts));
			commandList.put("start-mc", () -> CommandManagementCenterStart.apply(CLI.result, CLI.settings));
			//commandList.put("version", () -> );
			//commandList.put("tag", () -> );
			//commandList.put("config-file", () -> );
			commandList.put("cluster-shutdown", () -> CommandClusterShutdown.apply(CLI.result, CLI.settings));
			commandList.put("info", () -> CommandCliInfo.apply(CLI.result, CLI.settings));
			commandList.put("kill-member", () -> CommandShutdownMember.apply(CLI.result, CLI.hosts, CLI.settings));
			commandList.put("list-members", () -> CommandClusterListMember.apply(CLI.result, CLI.settings));
			commandList.put("list-member-tags", () -> CommandListMemberTags.apply());
			commandList.put("cluster-state", () ->  CommandClusterGetState.apply(CLI.result, CLI.settings));
			commandList.put("cluster-change-state", () ->  CommandClusterChangeState.apply(CLI.result, CLI.settings));
			commandList.put("set-master-member", () -> CommandSetMasterMember.apply(CLI.result, CLI.reader, CLI.hosts, CLI.settings));
			commandList.put("set-credentials", () -> CommandSetCredentials.apply(CLI.result, CLI.reader));
			commandList.put("cluster-force-start", () -> CommandForceStartMember.apply(CLI.result, CLI.hosts, CLI.settings));
			commandList.put("cluster-disconnect", () -> CommandClusterDisconnect.apply());
		}catch(Exception e){
			
		}
	}
	
    public OptionSet parse(String input) throws Exception {

        String[] inputArray = input.trim().split("[ \t]+");
        try {
            return this.optionParser.parse(inputArray);
        } catch (Exception e) {
            //TODO: Check if works/
            return this.parse("");
        }
    }

}
