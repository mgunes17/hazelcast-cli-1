package command.collection.queue;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;

public class CommandQueueSize {
	private static Logger logger = LoggerFactory.getLogger(CommandQueueSize.class);
	
	public static void apply() throws Exception {
		
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
		logger.trace("Printing size of the queue");
		System.out.println(queue.size());
	}
}
