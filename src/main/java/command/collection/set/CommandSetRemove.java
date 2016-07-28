package command.collection.set;

import java.util.Set;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandSetRemove {

	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace); 
		
		if(set.contains(result.nonOptionArguments().get(0))){
			set.remove(result.nonOptionArguments().get(0));
			System.out.println(true);
		}
		else{
			System.out.println(set.remove(result.nonOptionArguments().get(0)) + " is not exist");
		}
		
	}
}
