package command.collection.queue;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import command.collection.list.CommandListSet;

public class CommandQueuePoll {
	private static Logger logger = LoggerFactory.getLogger(CommandListSet.class);
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("queue") &&
				!DecisionToCreate.createDecision("queue")) {
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace); 
		
		try {
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(queue.poll()));
		} catch (Exception e) {
			logger.warn("JSON processing error", e);
			System.out.println("JsonProcessing Exception");
		}
		

	}
}
