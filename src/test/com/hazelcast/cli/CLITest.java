/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.cli;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

public class CLITest {

    String user = "ubuntu";
    String host = "54.84.254.202";
    int port = 22;

    @Test
    public void updateJar() throws Exception {
        String cmd = "scp -i /Users/mefeakengin/.ssh/id_rsa /Users/mefeakengin/.m2/repository/com/hazelcast/hazelcast/3.6-SNAPSHOT/hazelcast-3.6-SNAPSHOT.jar ubuntu@54.84.254.202:/home/ubuntu/hazelcast-3.6-SNAPSHOT.jar && " +
                     "cp /Users/mefeakengin/.m2/repository/com/example/test/hazelcast-cli/1.0-SNAPSHOT/hazelcast-cli-1.0-SNAPSHOT.jar /Users/mefeakengin/hazelcast/hazelcast-cli.jar";
        Runtime.getRuntime().exec(cmd);
    }

    @Test
    public void shellTest() throws Exception {
        String[] args1 = {"--install"};
        CLI.main(args1);
    }

    @Test
    public void installTest() throws Exception {
        String[] args1 = {"hazelcast", "--install", "--hostName", "efe", "--version", "3.5.4"};
        CLI.main(args1);
    }

    @Test
    //TODO: Test is assuming the default id_rsa as the public key
    public void startTest() throws Exception {
        String[] args1 = {"hazelcast", "--start", "--hostName", "ubuntu", "--clusterName", "istanbul", "--nodeName", "kadikoy", "--configFile", "/Users/mefeakengin/hazelcast/hazelcast.xml"};
        String[] args2 = {"hazelcast", "--start", "--hostname", "ubuntu", "--clustername", "istanbul", "--nodename", "uskudar", "--configfile", "/Users/mefeakengin/hazelcast/hazelcast.xml"};
        String[] args3 = {"hazelcast", "--start", "--hostname", "ubuntu", "--clustername", "istanbul", "--nodename", "besiktas", "--configfile", "/Users/mefeakengin/hazelcast/hazelcast.xml"};
        CLI.main(args1);
//        CLI.main(args2);
//        CLI.main(args3);
    }

    @Test
    //TODO: Test is assuming the default id_rsa as the public key
    public void shutdownTest() throws Exception {
        String[] args = {"--shutdown", "--hostName", "ubuntu"};
        CLI.main(args);
    }

    @Test
    //TODO: Test is assuming the default id_rsa as the public key
    public void listNodesTest() throws Exception {
        String[] args = {"hazelcast", "--list", "--hostname", "ubuntu"};
        CLI.main(args);
    }

    @Test
    //TODO: Test is assuming the default id_rsa as the public key
    public void killNodeTest() throws Exception {
        String[] args1 = {"hazelcast", "--killNode", "--hostname", "ubuntu"};
        String[] args2 = {"hazelcast", "--killNode", "--hostname", "ubuntu", "--port", "5702"};
        CLI.main(args1);
//        CLI.main(args2);
    }

    @Test
    //TODO: Test is assuming the default id_rsa as the public key
    public void startMCTest() throws Exception {
        String[] args = {"hazelcast", "--startMC", "--hostname", "ubuntu"};
        CLI.main(args);
    }

    @Test
    //TODO: Test is assuming the default id_rsa as the public key
    public void stateTest() throws Exception {
        String[] args = {"hazelcast", "--state", "--hostName", "ubuntu"};
        CLI.main(args);
    }

    @Test
    public void changeStateTest() throws Exception {
        String[] args = {"hazelcast", "--changeState", "passive", "--hostName", "ubuntu"};
        CLI.main(args);
    }

    @Test
    //TODO: Test is assuming the default id_rsa as the public key
    public void hazelcastArtTest() throws Exception {
        HazelcastArt artInstance = new HazelcastArt();
        System.out.println(artInstance.art);
    }

    //Weird: when the ip is 127. ..., shutdown works but not listnodes
    //Listnode command is not working with the private ip key starting with 54 ...

}
