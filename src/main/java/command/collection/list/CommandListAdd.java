package command.collection.list;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandListAdd {
	private static Logger logger = LoggerFactory.getLogger(CommandListAdd.class);
	
	public static void apply(OptionSet result) throws Exception {
		
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
		
		if(result.nonOptionArguments().size() != 1){
			logger.trace("Invalid parameters");
			System.out.println("Invalid parameters");		
		} else {
			list.add(result.nonOptionArguments().get(0));
			logger.trace(result.nonOptionArguments().get(0) + " was added to list");
			System.out.println(true);
		}
		
	}
}
