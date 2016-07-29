package command.collection.list;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;

public class CommandListClear {
	private static Logger logger = LoggerFactory.getLogger(CommandListClear.class);
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			logger.trace("Namespace is null");
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("list") &&
				!DecisionToCreate.createDecision("list")) {
			logger.trace("There is no list named " + CLI.nameSpace + " and not created");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		
		System.out.println("Are you sure you want to clear the list? (y/n)");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			list.clear();
			logger.trace(CLI.nameSpace + " list is cleared");
			System.out.println("Clear is OK");
		} else {
			logger.trace(CLI.nameSpace + " list is NOT cleared");
			System.out.println("NOT Cleared");
		}

	}
}
