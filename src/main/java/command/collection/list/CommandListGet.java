package command.collection.list;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandListGet {
	
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
					writeValueAsString(list.get((Integer) result.nonOptionArguments().get(0))));
		} catch (IndexOutOfBoundsException e ){
			System.out.println("Index is invalid");
		} 
		
	}

}
