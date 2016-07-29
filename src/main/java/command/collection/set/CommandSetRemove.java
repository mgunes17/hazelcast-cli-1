package command.collection.set;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class CommandSetRemove {
	private static Logger logger = LoggerFactory.getLogger(CommandSetSize.class);
	
	public static void apply(OptionSet result) throws Exception {
		
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
		
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			if(set.contains(result.nonOptionArguments().get(0))){
				logger.trace(result.nonOptionArguments().get(0) + " is removing");
				set.remove(result.nonOptionArguments().get(0));
				System.out.println(true);
			} else {
				logger.trace(set.remove(result.nonOptionArguments().get(0)) + " is not exist");
				System.out.println(set.remove(result.nonOptionArguments().get(0)) + " is not exist");
			}
		} else {
			logger.trace("NOT removed");
			System.out.println("False");
		}
		
		
		
	}
}
