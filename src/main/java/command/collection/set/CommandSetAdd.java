package command.collection.set;

import java.util.Set;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandSetAdd {

	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace); 
		set.add(result.nonOptionArguments().get(0));
		System.out.println(true);
	}
}
