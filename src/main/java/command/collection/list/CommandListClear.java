package command.collection.list;

import java.util.List;

import com.hazelcast.cli.CLI;

public class CommandListClear {

	public static void apply() throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		list.clear();
		System.out.println(true);
	}
}
