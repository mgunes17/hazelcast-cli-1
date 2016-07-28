package command.collection.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import command.collection.list.CommandListSet;
import joptsimple.OptionSet;

public class CommandMapGet {
	private static Logger logger = LoggerFactory.getLogger(CommandMapGet.class);
	
	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("map") &&
				!DecisionToCreate.createDecision("map")) {
			return;
		}
		
		IMap<Object,Object> map = CLI.instance.getMap(CLI.nameSpace); 
		
		try{
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(map.get(result.nonOptionArguments().get(0))));
		} catch (JsonProcessingException e) {
			logger.warn("JSON processing exception", e);
			System.out.println("Json processing error");
		}
		

	}
}
