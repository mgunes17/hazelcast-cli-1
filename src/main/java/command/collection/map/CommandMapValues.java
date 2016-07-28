package command.collection.map;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import command.collection.list.CommandListSet;

public class CommandMapValues {
	private static Logger logger = LoggerFactory.getLogger(CommandListSet.class);
	
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
			try {
				FieldsOfObject.displayObjectFields(new ObjectMapper().
						writeValueAsString(map.get(key)));
			} catch(JsonProcessingException e) {
				logger.warn("JSON processing exception", e);
				System.out.println("JsonProcessing Exception");
			}
			

		}
	}
}
