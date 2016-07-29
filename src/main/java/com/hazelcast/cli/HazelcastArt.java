package com.hazelcast.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * The art is by http://patorjk.com/software/taag/#p=display&h=1&f=Standard&t=hazelcast.
 *
 */
public class HazelcastArt {
	private static Logger logger = LoggerFactory.getLogger(HazelcastArt.class);
	
    public String art;

    public HazelcastArt(){
    	logger.trace("Hazelcast art is creating");
    	
        this.art =
                "  _                       _                   _   \n" +
                " | |__    __ _  ____ ___ | |  ___  __ _  ___ | |_ \n" +
                " | '_ \\  / _` ||_  // _ \\| | / __|/ _` |/ __|| __|\n" +
                " | | | || (_| | / /|  __/| || (__| (_| |\\__ \\| |_ \n" +
                " |_| |_| \\__,_|/___|\\___||_| \\___|\\__,_||___/ \\__|\n" +
                "\n";
    }

}
