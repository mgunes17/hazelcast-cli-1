package com.hazelcast.cli;

/**
 * Created by mefeakengin on 1/8/16.
 */
public class SshReturn {

    public final String message;
    public final int lineCounter;

    public SshReturn(String message, int lineCounter) {

        this.message = message;
        this.lineCounter = lineCounter;

    }

}
