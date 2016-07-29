package command.collection.set;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;

public class CommandSetSize {
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
		logger.trace("Printing size of the set");
		System.out.println(set.size());
	}
}
