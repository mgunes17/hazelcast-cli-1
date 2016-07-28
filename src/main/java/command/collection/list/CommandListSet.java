package command.collection.list;

import java.util.List;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import joptsimple.OptionSet;

public class CommandListSet {

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
			System.out.println("Index is invalid");
		} catch(ClassCastException e) {
			System.out.println("Please enter a index as integer");
		}
		
	}
}
