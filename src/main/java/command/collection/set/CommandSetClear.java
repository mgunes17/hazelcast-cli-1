package command.collection.set;

import java.util.Scanner;
import java.util.Set;

import com.hazelcast.cli.CLI;

public class CommandSetClear {

	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace);
		
		System.out.println("Are you sure you want to clear the set?(y/n");
		Scanner in = new Scanner(System.in);
		String decision = in.nextLine();
		
		if(decision.equalsIgnoreCase("y")){
			set.clear();
			System.out.println("Clear is OK");
		}
		else{
			System.out.println("NOT Cleared");
		}

	}
}
