package command.collection.queue;

import java.util.Queue;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandQueueOffer {

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
			System.out.println("Please enter a value to offer");
		}
		
	}
}
