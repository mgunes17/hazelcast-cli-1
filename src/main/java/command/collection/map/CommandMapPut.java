package command.collection.map;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

import joptsimple.OptionSet;

public class CommandMapPut {
	
	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
			
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace); 
		map.put(result.nonOptionArguments().get(0), result.nonOptionArguments().get(1));
	}
}
