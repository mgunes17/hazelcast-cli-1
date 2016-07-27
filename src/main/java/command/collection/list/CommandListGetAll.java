package command.collection.list;

import java.util.Iterator;
import java.util.List;

import com.hazelcast.cli.CLI;

public class CommandListGetAll {

	public static void apply() throws Exception{
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		Iterator<?> iterator = list.iterator();
		
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
	}
}
