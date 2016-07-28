package command.collection.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandMapGet {
	
	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			return;
		}
		
		IMap map = CLI.instance.getMap(CLI.nameSpace); 
		
		try{
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(map.get(result.nonOptionArguments().get(0))));
		} catch (JsonProcessingException e) {
			System.out.println("Json processing error");
		}
		

	}
}
