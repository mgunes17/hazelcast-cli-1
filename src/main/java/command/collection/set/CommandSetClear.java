package command.collection.set;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;

public class CommandSetClear {
	private static Logger logger = LoggerFactory.getLogger(CommandSetSize.class);
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			logger.trace("Namespace is null");
			System.out.println("Please define a namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("set") &&
				!DecisionToCreate.createDecision("set")) {
			logger.trace("There is no set named " + CLI.nameSpace + " and not created");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace);
		
		System.out.println("Are you sure you want to clear the set?(y/n");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			logger.trace("Clearing the set");
			set.clear();
			System.out.println("Clear is OK");
		} else {
			System.out.println("Set is NOT Cleared");
		}

	}
}
