package command.collection.map;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class CommandMapRemove {
	
	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			return;
		}
		
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace);
		
		System.out.println("Are you sure you want to remove from map? (y/n)");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			if(!map.containsKey(result.nonOptionArguments().get(0))) {
				System.out.println(result.nonOptionArguments().get(0) + " is not exist");
			} else if(map.isLocked(result.nonOptionArguments().get(0))) {
				System.out.println(result.nonOptionArguments().get(0) + " is locked");
			} else {
				map.remove(result.nonOptionArguments().get(0));
				System.out.println(true);
			}	
		} else {
			System.out.println("False");
		}	
		
	}
}
