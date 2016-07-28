package command.collection.queue;

import java.util.Queue;
import java.util.Scanner;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;

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
		Scanner in = new Scanner(System.in);
		String decision = in.nextLine();
		
		if(decision.equalsIgnoreCase("y")){
			queue.clear();
			System.out.println("Clear is OK");
		}
		else{
			System.out.println("NOT Cleared");
		}

	}
}
