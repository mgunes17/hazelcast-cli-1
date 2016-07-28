package command.collection.queue;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandQueueOffer {
	private static Logger logger = LoggerFactory.getLogger(CommandQueueOffer.class);
	
	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("queue") &&
				!DecisionToCreate.createDecision("queue")) {
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace);
		
		try {
			queue.offer(result.nonOptionArguments().get(0));
			System.out.println(true);
		} catch( IndexOutOfBoundsException e) {
			logger.warn("Offer value is null", e);
			System.out.println("Please enter a value to offer");
		}
		
	}
}
