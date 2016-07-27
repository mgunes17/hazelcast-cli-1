package command.collection.list;

import java.util.List;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandListContains {
	
	public static void apply(OptionSet result) throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		
		if(list.contains(result.nonOptionArguments().get(0)))
			System.out.println(true);
		else
			System.out.println(false);
	}
}
