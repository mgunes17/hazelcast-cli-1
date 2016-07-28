package command.collection.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;

public class CommandMapGetAll {
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			return;
		}
		
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace);
		
		for(Object key : map.keySet()){
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(key + " " + map.get(key)));
		}
	}
}
