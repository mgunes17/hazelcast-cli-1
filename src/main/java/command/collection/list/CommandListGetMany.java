package command.collection.list;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandListGetMany {
	private static Logger logger = LoggerFactory.getLogger(CommandListGetMany.class);
	
	public static void apply(OptionSet result) throws Exception{
		
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
		
		String[] index = String.valueOf(result.nonOptionArguments().get(0)).split(",");
		
		for(int i=0; i<index.length; i++){
			try {
				logger.trace("More element");
				FieldsOfObject.displayObjectFields(new ObjectMapper().
						writeValueAsString(list.get(Integer.parseInt(index[i]))));
			} catch(Exception e) {
				logger.warn("Invalid index");
				System.out.println("Invalid index");
			}
			

		}
	}
}
