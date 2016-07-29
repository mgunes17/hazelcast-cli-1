package command.collection.map;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;

public class CommandMapDestroy {
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			return;
		}
		
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace);
		
		System.out.println("Are you sure you want to destroy the map?(y/n");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			map.destroy();
			System.out.println("Destroy is OK");
		}
		else{
			System.out.println("NOT Destroyed");
		}

	}
}
