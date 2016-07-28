package command.collection.set;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.cli.CLI;

import command.collection.common.FieldsOfObject;
import command.collection.list.CommandListSet;

public class CommandSetGetAll {
	private static Logger logger = LoggerFactory.getLogger(CommandSetGetAll.class);
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		Set<Object> set = CLI.instance.getSet(CLI.nameSpace); 
		
		Iterator<?> iterator = set.iterator();
		while ( iterator.hasNext() ) {
			try {
				FieldsOfObject.displayObjectFields(new ObjectMapper().
						writeValueAsString(iterator.next()));
			} catch(JsonProcessingException e) {
				logger.warn("JSON processing exception", e);
				System.out.println("Json Processing Exception");
			}	
		}
	}
}
