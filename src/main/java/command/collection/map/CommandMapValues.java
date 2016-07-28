package command.collection.map;

import java.util.Map;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;

public class CommandMapValues {
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			return;
		}
			
		Map map = CLI.instance.getMap(CLI.nameSpace);
		
		for(Object key : map.keySet()){
			System.out.println(map.get(key));
		}
	}
}
