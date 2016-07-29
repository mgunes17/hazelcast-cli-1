package command.collection.common;

import com.hazelcast.cli.CLI;

import jline.console.ConsoleReader;

public class DecisionToCreate {
	
	public static boolean createDecision(String collectionType) throws Exception {
		
		System.out.println("There is no "+ collectionType + " named "+ CLI.nameSpace);
		System.out.println("Would you like to create? (y/n)");
		
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")) {
			return true;
		} else {
			return false;
		}
			
	}
}
