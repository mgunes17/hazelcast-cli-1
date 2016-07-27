package command.collection.map;

import java.util.Map;

import com.hazelcast.cli.CLI;

public class CommandMapClear {
	
	public static void apply() throws Exception {
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
			
		Map map = CLI.instance.getMap(CLI.nameSpace);
		map.clear();
	}
}
