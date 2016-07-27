package command.collection.list;

import java.util.List;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandListRemove {

	public static void apply(OptionSet result) throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		list.remove(result.nonOptionArguments().get(0));
	}
}
