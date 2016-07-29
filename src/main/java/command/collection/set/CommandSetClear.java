package command.collection.set;

import java.util.Set;

import com.hazelcast.cli.CLI;

import jline.console.ConsoleReader;

public class CommandSetClear {

	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace);
		
		System.out.println("Are you sure you want to clear the set?(y/n");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			set.clear();
			System.out.println("Clear is OK");
		} else {
			System.out.println("NOT Cleared");
		}

	}
}
