package command.collection.queue;

import java.util.Queue;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandQueueOfferMany {

	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("queue") &&
				!DecisionToCreate.createDecision("queue")) {
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace); 
		
		String[] values = ((String) result.nonOptionArguments().get(1)).split(",");
		
		for(int i=0; i<(Integer)result.nonOptionArguments().get(0); i++){
			queue.offer(values[i]);
			System.out.println(true);
		}
	}
}
