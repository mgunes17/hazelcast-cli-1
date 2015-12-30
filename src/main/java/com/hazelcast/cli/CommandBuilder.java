package com.hazelcast.cli;

/**
 * Created by emrah on 12/06/15.
 */
//Assumption: The ssh executor works from folder itself
public class CommandBuilder {

    private static final String HZ_DOWNLOAD_URL = "'http://download.hazelcast.com/download.jsp?version=hazelcast-#version#&type=tar&p=153008119'";
//    http://download.hazelcast.com/download.jsp?version=hazelcast-3.6-EA3&p=153008119

    public String wget(String version) {
        String url = HZ_DOWNLOAD_URL.replace("#version#", version);
        //for osx curl -o /Users/mefeakengin/hazelcast/hazelcast.tar.gz 'http://download.hazelcast.com/download.jsp?version=hazelcast-3.5.4&type=tar&p=153008119'
//        return "curl -o ~/hazelcast/hazelcast" + ".tar.gz "  + url;
        return "wget -O hazelcast" + ".tar.gz " + url ;
    }

    public String extract() {
        return "tar -zxvf hazelcast" + ".tar.gz";
    }

    public String move(String original, String target ) {
        return "cp " + original + " " + target;
    }

    public String start(String configFile) {
        //java -cp "lib/*" -Dhazelcast.config=hazelcast-istanbul.xml com.hazelcast.core.server.StartServer & echo $!
        //TODO: Put path instead of /home/ubuntu
        return "/home/ubuntu/hazelcast/bin/server.sh";
//        return "java -cp \"/home/ubuntu/hazelcast/lib/*\" -Dhazelcast.config=" +
//                configFile + " com.hazelcast.core.server.StartServer & echo $!";
    }

    public String startMC() {
        return "java -jar /home/ubuntu/hazelcast/mancenter/mancenter-3.5.4.war";
    }

    public String upload(String user, String hostName, String nodeName, String configFile) {
        if(configFile == null){
            configFile = "bin/hazelcast.xml";
        }
        //scp bin/hazelcast.xml mefeakengin@localhost:~/hazelcast-istanbul.xml
        //While copying, put the configFile into the hazelcast installation path
        //TODO: Put path instead of /home/ubuntu
        return "scp " + configFile + " " + user + "@" + hostName + ":/home/ubuntu/" + "hazelcast-" + nodeName + ".xml";
    }

    public String shutdown(String hostIp, String clusterPort, String groupName, String password) {
        //default: curl --data "dev&dev-pass" http://127.0.0.1:5701/hazelcast/rest/management/cluster/shutdown
        return "curl --data \"" + groupName + "&" + password + "\" http://" + hostIp + ":" + clusterPort + "/hazelcast/rest/management/cluster/shutdown";
    }

}
