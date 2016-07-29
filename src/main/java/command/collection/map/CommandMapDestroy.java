package command.collection.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;

public class CommandMapDestroy {
	private static Logger logger = LoggerFactory.getLogger(CommandMapDestroy.class);
	
	public static void apply() throws Exception {
		
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
		
		System.out.println("Are you sure you want to destroy the map?(y/n");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			map.destroy();
			logger.trace("map is destroyed");
			System.out.println("Destroy is OK");
		}
		else{
			logger.trace("map is not destroyed");
			System.out.println("NOT Destroyed");
		}

	}
}
