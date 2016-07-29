package command.collection.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;

public class CommandMapKeys {
	private static Logger logger = LoggerFactory.getLogger(CommandMapKeys.class);
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			logger.trace("Namespace is null");
			System.out.println("Please define a namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			logger.trace("There is no map named " + CLI.nameSpace + " and not created");
			return;
		}
			
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace); 
		
		for(Object key : map.keySet()){
			logger.trace("More keys");
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(key));
		}
	}
}
