package command.collection.queue;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandQueuePollMany {
	private static Logger logger = LoggerFactory.getLogger(CommandQueuePollMany.class);
	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			logger.trace("Namespace is null");
			System.out.println("Please define a namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("queue") &&
				!DecisionToCreate.createDecision("queue")) {
			logger.trace("There is no queue named " + CLI.nameSpace + " and not created");
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace); 
		
		try {
			for(int i=0; i<Integer.valueOf((String)result.nonOptionArguments().get(0)); i++){
				logger.trace("Polling the queue");
				FieldsOfObject.displayObjectFields(new ObjectMapper().
						writeValueAsString(queue.poll()));
			}
		} catch (NumberFormatException e){
			logger.warn("Input type is not integer", e);
			System.out.println("Please enter a value as integer");
		}
			
	}
}
