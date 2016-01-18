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

//Assumption: The ssh executor works from folder itself
public class CommandBuilder {

    private static final String HZ_DOWNLOAD_URL = "\"http://download.hazelcast.com/download.jsp?version=hazelcast-#version#&type=tar&p=153008119\"";
//    http://download.hazelcast.com/download.jsp?version=hazelcast-3.6-EA3&p=153008119

    public String wget(String version, String path) {
        String url = HZ_DOWNLOAD_URL.replace("#version#", version);
        //for osx curl -o /Users/mefeakengin/hazelcast/hazelcast.tar.gz 'http://download.hazelcast.com/download.jsp?version=hazelcast-3.5.4&type=tar&p=153008119'
//        return "curl -o ~/hazelcast/hazelcast" + ".tar.gz "  + url;
        return "wget -O " + path + "/hazelcast" + ".tar.gz " + url ;
    }

    public String extract(String path) {
        return "tar -zxvf " + path + "/hazelcast" + ".tar.gz";
    }

    public String move(String original, String target) {
        return "cp -r " + original + " " + target;
    }

    public String upload(String user, String hostName, String nodeName, String configFile, String path) {
        if(configFile == null){
            configFile = path + "hazelcast/bin/hazelcast.xml";
        }
        //scp bin/hazelcast.xml mefeakengin@localhost:~/hazelcast-istanbul.xml
        return "scp " + configFile + " " + user + "@" + hostName + ":" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml";
    }

    public String start(String path, String nodeName) {
        //java -cp "lib/*" -Dhazelcast.config=hazelcast-istanbul.xml com.hazelcast.core.server.StartServer & echo $!
//        return  "touch " + path + "/hazelcast/bin/log-" + nodeName + ".null && " +
//                "touch " + path + "/hazelcast/bin/log-" + nodeName + ".out && " +
//                "nohup java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
//                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " +
//                "com.hazelcast.core.server.StartServer > " +
//                path + "/hazelcast/bin/log-" + nodeName + ".null 2> " +
//                path + "/hazelcast/bin/log-" + nodeName + ".out < /dev/null & echo $!";
        return  "java -cp \"" + path + "/hazelcast/hazelcast.jar\" " +
                "-Dhazelcast.config=" + path + "/hazelcast/bin/hazelcast-" + nodeName + ".xml " +
                "com.hazelcast.core.server.StartServer & echo $!";
    }

    public String shutdown(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/shutdown
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/shutdown";
    }

    public String listNodes(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/nodes
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/nodes";
    }


    public String killNode(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/killNode
        return "curl --data \"" + groupName + "&" + password + "&" + hostIp + "&" + clusterPort + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/killNode";
    }

    public String state(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/state
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/state";
    }

    public String changeState(String hostIp, String clusterPort, String groupName, String password, String stateParam) {
        //default: curl --data "dev&dev-pass&active" http://127.0.0.1:5701/hazelcast/rest/management/cluster/changeState
        return "curl --data \"" + groupName + "&" + password + "&" + stateParam + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/changeState";
    }

    public String copyLog(String user, String hostName, String remotePath, String localPath) {
        return "scp " + user + "@" + hostName + ":" + remotePath + " " + localPath;
    }

    public String printLog(String localPath, int oldLogLength) {
        oldLogLength = 10;
//        localPath = "/Users/mefeakengin/hazelcast/deneme.out";
        return "cat " + localPath + " | tail -n +" + (oldLogLength + 1) + " > /Users/mefeakengin/hazelcast/deneme.out";
    }

    public String startMC(String path, String hostIp) {
        return "java -jar " + path + "/hazelcast/mancenter.war &&" + "echo " + "Management Center is established at " + hostIp + ":8080/mancenter &";
    }

}



//        return "nohup java -cp \"/home/ubuntu/hazelcast/lib/*\" -Dhazelcast.config=" + configFile + " com.hazelcast.core.server.StartServer 2>&1 >> /Users/mefeakengin/baskuzum.log & /bin/echo $! > /Users/mefeakengin/baskuzum.pid";
//            return "nohup java -cp \"/home/ubuntu/hazelcast/lib/*\" -Dhazelcast.config=" + configFile + " com.hazelcast.core.server.StartServer 2>&1 >> /home/ubuntu/baskuzum.log | tail -f /home/ubuntu/baskuzum.log  >> /Users/mefeakengin/local.log  & /bin/echo $! > /home/ubuntu/baskuzum.pid";
