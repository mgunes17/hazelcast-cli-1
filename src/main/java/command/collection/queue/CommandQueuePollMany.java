package command.collection.queue;

import java.util.Queue;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandQueuePollMany {

	public static void apply(OptionSet result) throws Exception {
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace); 
		
		for(int i=0; i<(Integer)result.nonOptionArguments().get(0); i++)
			System.out.println(queue.poll());
	}
}
