package command.collection.memory;

import org.github.jamm.MemoryMeter;

import com.hazelcast.cli.CLI;
import com.hazelcast.core.DistributedObject;

import joptsimple.OptionSet;

public class CommandMemoryAll {

	public static void apply(OptionSet result) throws Exception {
		MemorySizeType mst;
		
		if(result.nonOptionArguments().isEmpty()) {
			mst = MemorySizeType.valueOf("b");
		} else {
			mst = MemorySizeType.valueOf(String.valueOf(result.nonOptionArguments().get(0)));
		}
		
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","Type","Name","Size("+mst.getTypeName()+")" );
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","-----","-----","-----" );
		
		for(DistributedObject c : CLI.instance.getDistributedObjects()){
        	System.out.printf("%-20.20s %-20.20s", c.getServiceName(), c.getName());
        	MemoryMeter meter = new MemoryMeter();
            System.out.printf((double)meter.measureDeep(c)/mst.getSize() +"\n");
		}
	}
	
	public static void collectionFilter(OptionSet result, String collectionType) throws Exception {
		MemorySizeType mst;
		
		if(result.nonOptionArguments().isEmpty()) {
			mst = MemorySizeType.valueOf("b");
		} else {
			mst = MemorySizeType.valueOf(String.valueOf(result.nonOptionArguments().get(0)));
		}
		
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","Type","Name","Size("+mst.getTypeName()+")" );
		System.out.printf("%-20.20s %-20.20s %-20.20s\n","-----","-----","-----" );
		
		for(DistributedObject c : CLI.instance.getDistributedObjects()){
			if(c.getClass().getName().contains(collectionType)) {
				System.out.printf("%-20.20s %-20.20s", c.getServiceName(), c.getName());
	        	MemoryMeter meter = new MemoryMeter();
	            System.out.printf((double)meter.measureDeep(c)/mst.getSize() +"\n");
			}
        	
		}
	}
}
