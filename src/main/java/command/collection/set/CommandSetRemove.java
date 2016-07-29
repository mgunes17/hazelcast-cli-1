package command.collection.set;

import java.util.Set;

import com.hazelcast.cli.CLI;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class CommandSetRemove {

	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace); 
		
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			if(set.contains(result.nonOptionArguments().get(0))){
				set.remove(result.nonOptionArguments().get(0));
				System.out.println(true);
			} else {
				System.out.println(set.remove(result.nonOptionArguments().get(0)) + " is not exist");
			}
		} else {
			System.out.println("False");
		}
		
		
		
	}
}
