package command.collection.common;

import com.fasterxml.jackson.core.JsonProcessingException;

public class FieldsOfObject {

	public static void displayObjectFields(String objectAsString){
		String[] fields = objectAsString.split(",");
		
		for(String s : fields ){
			System.out.println(s);
		}	
	}
}
