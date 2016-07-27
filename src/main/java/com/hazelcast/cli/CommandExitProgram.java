package com.hazelcast.cli;


public class CommandExitProgram {

    public static void apply() throws Exception {

        //TODO: Improve the exit!
        System.out.println("Exiting the program ... ");
        CLI.instance.shutdown();
        System.exit(-1);

    }
}