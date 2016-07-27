package command.collection.list;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.hazelcast.cli.CLI;

import joptsimple.OptionSet;

public class CommandListAdd {

	public static void apply(OptionSet result) throws Exception {
		
		if(CLI.nameSpace == null){
			System.out.println("Please define namespace");
			return;
		}
		
		List<Object> list = CLI.instance.getList(CLI.nameSpace); 
		list.add(result.nonOptionArguments().get(0));
		System.out.println(true);
	}
}
