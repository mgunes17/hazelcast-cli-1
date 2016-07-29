package command.collection.set;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;

public class CommandSetGetAll {
	private static Logger logger = LoggerFactory.getLogger(CommandSetGetAll.class);
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			logger.trace("Namespace is null");
			System.out.println("Please define a namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("set") &&
				!DecisionToCreate.createDecision("set")) {
			logger.trace("There is no set named " + CLI.nameSpace + " and not created");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace); 
		
		Iterator<?> iterator = set.iterator();
		while ( iterator.hasNext() ) {
			try {
				logger.trace("Iterating the set");
				FieldsOfObject.displayObjectFields(new ObjectMapper().
						writeValueAsString(iterator.next()));
			} catch(JsonProcessingException e) {
				logger.warn("JSON processing exception", e);
				System.out.println("Json Processing Exception");
			}	
		}
	}
}
