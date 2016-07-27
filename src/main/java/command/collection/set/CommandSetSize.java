package command.collection.set;

import java.util.Set;

import com.hazelcast.cli.CLI;

public class CommandSetSize {

	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace); 
		System.out.println(set.size());
	}
}
