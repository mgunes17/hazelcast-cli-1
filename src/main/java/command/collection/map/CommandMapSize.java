package command.collection.map;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.IMap;

public class CommandMapSize {
	public static void apply(){
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		IMap<Object, Object> map = CLI.instance.getMap(CLI.nameSpace);
		System.out.println(map.size());
	}
}
