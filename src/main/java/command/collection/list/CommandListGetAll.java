package command.collection.list;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;

public class CommandListGetAll {
	private static Logger logger = LoggerFactory.getLogger(CommandListGetAll.class);
	
	public static void apply() throws Exception{
		
		if(CLI.nameSpace == null){
			logger.trace("Namespace is null");
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("list") &&
				!DecisionToCreate.createDecision("list")) {
			logger.trace("There is no list named " + CLI.nameSpace + " and not created");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		Iterator<?> iterator = list.iterator();
		
		while(iterator.hasNext()){
			logger.trace("More element");
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(iterator.next()));
		}
		
	}
}
