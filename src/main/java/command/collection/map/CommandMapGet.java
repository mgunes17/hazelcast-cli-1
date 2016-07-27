package command.collection.map;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import joptsimple.OptionSet;

public class CommandMapGet {
	
	public static void apply(OptionSet result){
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		IMap map = CLI.instance.getMap(CLI.nameSpace); 
		System.out.println(map.get(result.nonOptionArguments().get(0)));
	}
}
