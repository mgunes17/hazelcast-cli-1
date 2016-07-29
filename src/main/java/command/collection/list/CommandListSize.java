package command.collection.list;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;

public class CommandListSize {
	private static Logger logger = LoggerFactory.getLogger(CommandListSize.class);
	
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
		logger.trace("Printing list size");
		System.out.println(list.size());
	}

}
