package command.collection.list;

import java.util.List;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandListSet {

	public static void apply(OptionSet result) throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		list.set((Integer) result.nonOptionArguments().get(0), result.nonOptionArguments().get(1));
	}
}
