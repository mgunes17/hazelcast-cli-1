package command.collection.common;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.DistributedObject;

public class FindCollectionName {
	private static Logger logger = LoggerFactory.getLogger(FindCollectionName.class);
	
	public static boolean isExistCollectionName(String type) throws Exception {
		Collection<DistributedObject> distributedObject = CLI.instance.getDistributedObjects();
		int i = 0;
		boolean found = false;
		Iterator<DistributedObject> iterator = distributedObject.iterator();
		DistributedObject distObject;
		
		while(iterator.hasNext() && found == false) {
			logger.trace("More distributed object-not found");
			distObject = iterator.next();
			
			if(distObject.getName().equalsIgnoreCase(CLI.nameSpace) && 
					distObject.getClass().getName().contains(type)){
				logger.trace("Found distributed object named " +CLI.nameSpace);
				found = true;
			}
				
		}
		
		return found;

	}
	
}
