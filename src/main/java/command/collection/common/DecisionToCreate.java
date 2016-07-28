package command.collection.common;

import java.util.Scanner;

import com.hazelcast.cli.CLI;

public class DecisionToCreate {
	
	public static boolean createDecision(String collectionType) throws Exception {
		
		System.out.println("There is no "+ collectionType + " named "+ CLI.nameSpace);
		System.out.println("Would you like to create? (y/n)");
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String decision = in.nextLine();
		
		if(decision.equalsIgnoreCase("y"))
			return true;
		else
			return false;
	}
}
