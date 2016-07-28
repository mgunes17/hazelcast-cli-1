package command.collection.list;

import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FieldsOfObject;
import command.collection.common.FindCollectionName;

public class CommandListGetAll {

	public static void apply() throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("list") &&
				!DecisionToCreate.createDecision("list")) {
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		Iterator<?> iterator = list.iterator();
		
		while(iterator.hasNext()){
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(iterator.next()));
		}
		
	}
}
