package command.namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.DistributedObject;

import joptsimple.OptionSet;

public class CommandNSSet {
	private static Logger logger = LoggerFactory.getLogger(CommandNSSet.class);
	
	public static void apply(OptionSet result) throws Exception {
		CLI.nameSpace = (String) result.nonOptionArguments().get(0);
		
		int s = 0;
		
		for(DistributedObject distributedObject : CLI.instance.getDistributedObjects()){
			if(distributedObject.getName().equalsIgnoreCase(CLI.nameSpace)){
				logger.trace("Found a new distributed object named as" + CLI.nameSpace);
				System.out.println(distributedObject.getServiceName() + " " + distributedObject.getName());
				s++;
			}
		}
		
		if(s == 0){
			logger.trace("There is no collection named as " + CLI.nameSpace);
			System.out.println("There is no collection named as " + CLI.nameSpace);
		} else {
			logger.trace(s + " collection(s) found named as " + CLI.nameSpace);
			System.out.println(s + " collection(s) found named as " + CLI.nameSpace);
		}
	}
}
