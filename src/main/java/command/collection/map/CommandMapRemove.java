package command.collection.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class CommandMapRemove {
	private static Logger logger = LoggerFactory.getLogger(CommandMapRemove.class);
	
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
		
		System.out.println("Are you sure you want to remove from map? (y/n)");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			if(!map.containsKey(result.nonOptionArguments().get(0))) {
				logger.trace(result.nonOptionArguments().get(0) + " is not exist");
				System.out.println(result.nonOptionArguments().get(0) + " is not exist");
			} else if(map.isLocked(result.nonOptionArguments().get(0))) {
				logger.trace(result.nonOptionArguments().get(0) + " is locked");
				System.out.println(result.nonOptionArguments().get(0) + " is locked");
			} else {
				map.remove(result.nonOptionArguments().get(0));
				logger.trace("Remove is OK");
				System.out.println(true);
			}	
		} else {
			logger.trace("NOT removed");
			System.out.println("False");
		}	
		
	}
}
