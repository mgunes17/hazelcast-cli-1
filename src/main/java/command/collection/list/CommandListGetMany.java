package command.collection.list;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandListGetMany {

	public static void apply(OptionSet result) throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("list") &&
				!DecisionToCreate.createDecision("list")) {
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		
		String[] index = String.valueOf(result.nonOptionArguments().get(0)).split(",");
		
		for(int i=0; i<index.length; i++){
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(list.get(Integer.parseInt(index[i]))));

		}
	}
}
