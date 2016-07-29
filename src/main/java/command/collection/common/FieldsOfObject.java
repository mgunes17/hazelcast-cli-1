package command.collection.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldsOfObject {
	private static Logger logger = LoggerFactory.getLogger(FieldsOfObject.class);
	
	public static void displayObjectFields(String objectAsString){
		logger.trace("Splitting fields");
		String[] fields = objectAsString.split(",");
		
		for(String s : fields ){
			logger.trace("More field");
			System.out.println(s);
		}	
	}
}
