package command.namespace;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.DistributedObject;

import joptsimple.OptionSet;

public class CommandNSSet {
	
	public static void apply(OptionSet result) throws Exception {
		CLI.nameSpace = (String) result.nonOptionArguments().get(0);
		
		int s = 0;
		
		for(DistributedObject distributedObject : CLI.instance.getDistributedObjects()){
			if(distributedObject.getName().equalsIgnoreCase(CLI.nameSpace)){
				System.out.println(distributedObject.getServiceName() + " " + distributedObject.getName());
				s++;
			}
		}
		
		if(s == 0){
			System.out.println("There is no collection named as " + CLI.nameSpace);
		} else {
			System.out.println(s + " collection(s) found named as " + CLI.nameSpace);
		}
	}
}
