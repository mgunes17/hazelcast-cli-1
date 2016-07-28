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

public class CommandListGet {
	private static Logger logger = LoggerFactory.getLogger(CommandListSet.class);
	
	public static void apply(OptionSet result) throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("list") &&
				!DecisionToCreate.createDecision("list")) {
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		
		try {
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(list.get(Integer.valueOf((String)result.nonOptionArguments().get(0)))));
		} catch (IndexOutOfBoundsException e ){
			logger.warn("Invalid index", e);
			System.out.println("Index is invalid");
		} catch(NumberFormatException e){
			logger.warn("İnvalid index", e);
			System.out.println("Please enter an integer value for index");
		}
		
	}

}
