package command.collection.queue;

import java.util.Queue;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;

public class CommandQueueClear {

	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("queue") &&
				!DecisionToCreate.createDecision("queue")) {
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace); 
		
		System.out.println("Are you sure you want to clear the queue?(y/n");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			queue.clear();
			System.out.println("Clear is OK");
		} else {
			System.out.println("NOT Cleared");
		}

	}
}
