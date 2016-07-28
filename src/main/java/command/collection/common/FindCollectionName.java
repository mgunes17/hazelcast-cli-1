package command.collection.common;

import java.util.Collection;
import java.util.Iterator;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.DistributedObject;

public class FindCollectionName {

	public static boolean isExistCollectionName(String type) throws Exception {
		Collection<DistributedObject> distributedObject = CLI.instance.getDistributedObjects();
		int i = 0;
		boolean found = false;
		Iterator<DistributedObject> iterator = distributedObject.iterator();
		DistributedObject distObject;
		
		while(iterator.hasNext() && found == false) {
			distObject = iterator.next();
			
			if(distObject.getName().equalsIgnoreCase(CLI.nameSpace) && 
					distObject.getClass().getName().contains(type))
				found = true;
		}
		
		return found;

	}
	
}
