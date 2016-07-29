package command.collection.queue;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import command.collection.memory.CommandMemoryAll;
import jline.console.ConsoleReader;

public class CommandQueueClear {
	private static Logger logger = LoggerFactory.getLogger(CommandQueueClear.class);
	
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
		
		System.out.println("Are you sure you want to clear the queue?(y/n");
		logger.trace("Reading input");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			logger.trace("Clearing the queue");
			queue.clear();
			System.out.println("Clear is OK");
		} else {
			logger.trace("Queue is NOT cleared");
			System.out.println("NOT Cleared");
		}

	}
}
