package command.collection.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandMapLock {
	private static Logger logger = LoggerFactory.getLogger(CommandMapLock.class);
	
	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			logger.trace("Namespace is null");
			System.out.println("Please define a namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			logger.trace("There is no map named " + CLI.nameSpace + " and not created");
			return;
		}
		
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace);
		
		map.lock(result.nonOptionArguments().get(0));
		logger.trace(result.nonOptionArguments().get(0) + " is locked");
		System.out.println(true);
	}
}
