package command.collection.list;

import java.util.List;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;

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
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			list.clear();
			System.out.println("Clear is OK");
		} else {
			System.out.println("NOT Cleared");
		}

	}
}
