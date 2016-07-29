package command.collection.memory;

import org.github.jamm.MemoryMeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.DistributedObject;

import joptsimple.OptionSet;

public class CommandMemoryAll {
	private static Logger logger = LoggerFactory.getLogger(CommandMemoryAll.class);
	
	public static void apply(OptionSet result) throws Exception {
		MemorySizeType mst;
		
		if(result.nonOptionArguments().isEmpty()) {
			mst = MemorySizeType.valueOf("b");
			logger.trace("Memory type is byte (default");
		} else {
			logger.trace("Memory type is " + result.nonOptionArguments().get(0));
			mst = MemorySizeType.valueOf(String.valueOf(result.nonOptionArguments().get(0)));
		}
		
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","Type","Name","Size("+mst.getTypeName()+")" );
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","-----","-----","-----" );
		
		for(DistributedObject c : CLI.instance.getDistributedObjects()){
			logger.trace("Printing memory size of " + c.getServiceName());
        	System.out.printf("%-20.20s %-20.20s", c.getServiceName(), c.getName());
        	MemoryMeter meter = new MemoryMeter();
            System.out.printf((double)meter.measureDeep(c)/mst.getSize() +"\n");
		}
	}
	
	public static void collectionFilter(OptionSet result, String collectionType) throws Exception {
		MemorySizeType mst;
		
		if(result.nonOptionArguments().isEmpty()) {
			logger.trace("Memory type is byte (default) for " + collectionType);
			mst = MemorySizeType.valueOf("b");
		} else {
			logger.trace("Memory type is " + result.nonOptionArguments().get(0) + " for " + collectionType);
			mst = MemorySizeType.valueOf(String.valueOf(result.nonOptionArguments().get(0)));
		}
		
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","Type","Name","Size("+mst.getTypeName()+")" );
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","-----","-----","-----" );
		
		for(DistributedObject c : CLI.instance.getDistributedObjects()){
			if(c.getClass().getName().contains(collectionType)) {
				logger.trace("Printing memory size of " +  c.getServiceName());
				System.out.printf("%-20.20s %-20.20s", c.getServiceName(), c.getName());
	        	MemoryMeter meter = new MemoryMeter();
	            System.out.printf((double)meter.measureDeep(c)/mst.getSize() +"\n");
			}
        	
		}
	}
}
