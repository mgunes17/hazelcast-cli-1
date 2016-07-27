package command.collection.queue;

import java.util.Queue;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandQueueOffer {

	public static void apply(OptionSet result) throws Exception {
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace); 
		queue.offer(result.nonOptionArguments().get(0));
		System.out.println(true);
	}
}
