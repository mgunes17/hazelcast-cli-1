package command.collection.set;

import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.FieldsOfObject;

public class CommandSetGetAll {

	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace); 
		
		Iterator<?> iterator = set.iterator();
		while ( iterator.hasNext() ) {
			FieldsOfObject.displayObjectFields(new ObjectMapper().
					writeValueAsString(iterator.next()));
		}
	}
}
