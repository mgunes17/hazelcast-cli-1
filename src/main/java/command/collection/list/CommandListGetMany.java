package command.collection.list;

import java.util.List;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandListGetMany {

	public static void apply(OptionSet result) throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		
		String[] index = String.valueOf(result.nonOptionArguments().get(0)).split(",");
		
		for(int i=0; i<index.length; i++){
			list.get(Integer.parseInt(index[i]));
		}
	}
}
