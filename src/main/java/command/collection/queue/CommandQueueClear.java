package command.collection.queue;

import java.util.Queue;

import com.hazelcast.cli.CLI;

public class CommandQueueClear {

	public static void apply() throws Exception {
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Queue<Object> queue = CLI.instance.getQueue(CLI.nameSpace); 
		queue.clear();
	}
}
