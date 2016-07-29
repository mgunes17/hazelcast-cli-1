package command.collection.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.cli.CLI;

import jline.console.ConsoleReader;

public class DecisionToCreate {
	private static Logger logger = LoggerFactory.getLogger(DecisionToCreate.class);
	
	public static boolean createDecision(String collectionType) throws Exception {
		
		System.out.println("There is no "+ collectionType + " named "+ CLI.nameSpace);
		System.out.println("Would you like to create? (y/n)");
		
		logger.trace("Reading decision");
		ConsoleReader reader = new ConsoleReader();
		String decision = reader.readLine();
		
		if(decision.equalsIgnoreCase("y")) {
			logger.trace("Decision is y");
			return true;
		} else {
			logger.trace("Decision is not y");
			return false;
		}
			
	}
}
