package command.collection.map;

import java.util.Map;
import java.util.Scanner;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;

public class CommandMapClear {
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			return;
		}
		
		Map map = CLI.instance.getMap(CLI.nameSpace);

		
		System.out.println("Are you sure you want to clear the map?(y/n");
		Scanner in = new Scanner(System.in);
		String decision = in.nextLine();
		
		if(decision.equalsIgnoreCase("y")){
			map.clear();
			System.out.println("Clear is OK");
		}
		else{
			System.out.println("NOT Cleared");
		}
			
	}
}
