package command.collection.map;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

public class CommandMapKeys {
	
	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
			
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace); 
		
		for(Object key : map.keySet()){
			System.out.println(key);
		}
	}
}
