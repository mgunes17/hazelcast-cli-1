package command.collection.list;

import java.util.List;
import java.util.Scanner;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;

public class CommandListClear {

	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("list") &&
				!DecisionToCreate.createDecision("list")) {
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		System.out.println("Are you sure you want to clear the list? (y/n)");
		Scanner in = new Scanner(System.in);
		String decision = in.nextLine();
		
		if(decision.equalsIgnoreCase("y")){
			list.clear();
			System.out.println("Clear is OK");
		}
		else{
			System.out.println("NOT Cleared");
		}

	}
}
