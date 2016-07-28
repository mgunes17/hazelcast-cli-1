package command.collection.common;

public class FieldsOfObject {

	public static void displayObjectFields(String objectAsString){
		try{
			String[] fields = objectAsString.split(",");
			
			for(String s : fields ){
				System.out.println(s);
			}	
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Index is invalid");
		}
	}
}
