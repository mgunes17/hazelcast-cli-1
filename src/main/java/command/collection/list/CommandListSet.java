package command.collection.list;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandListSet {
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
			list.set((Integer.valueOf((String)result.nonOptionArguments().get(0))), result.nonOptionArguments().get(1));
		} catch(IndexOutOfBoundsException e) {
			logger.warn("Index is invalid", e);
			System.out.println("Index is invalid");
		} catch(ClassCastException e) {
			logger.warn("Index is not an integer" );
			System.out.println("Please enter a index as integer");
		}
		
	}
}
