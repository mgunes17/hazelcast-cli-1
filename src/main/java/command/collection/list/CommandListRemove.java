package command.collection.list;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import command.collection.common.DecisionToCreate;
import command.collection.common.FindCollectionName;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class CommandListRemove {
	private static Logger logger = LoggerFactory.getLogger(CommandListRemove.class);
	
	public static void apply(OptionSet result) throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		} else if(!FindCollectionName.isExistCollectionName("list") &&
				!DecisionToCreate.createDecision("list")) {
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		
		System.out.println("Are you sure you want to remove from the list? (y/n)");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")){
			if(!list.contains(result.nonOptionArguments().get(0))) {
				System.out.println(result.nonOptionArguments().get(0) + " is not exist");
			} else {
				try {
					list.remove(result.nonOptionArguments().get(0));
					System.out.println("Remove is OK");
				} catch(ArrayIndexOutOfBoundsException e) {
					logger.warn("Index is invalid", e);
					System.out.println("Index is invalid");
				}
			}
		} else {
			System.out.println("NOT Removed");
		}		
		
	}
}
