package com.hazelcast.cli;

/**
 * Created by emrah on 12/06/15.
 */
//Assumption: The ssh executor works from folder itself
public class CommandBuilder {

    private static final String HZ_DOWNLOAD_URL = "'http://download.hazelcast.com/download.jsp?version=hazelcast-3.5.4&type=tar&p=153008119'";

    public String wget(String version) {
        String url = HZ_DOWNLOAD_URL.replace("#version#", version);
        //for osx curl -o /Users/mefeakengin/hazelcast/hazelcast.tar.gz 'http://download.hazelcast.com/download.jsp?version=hazelcast-3.5.4&type=tar&p=153008119'
        return "curl -o /Users/mefeakengin/hazelcast/hazelcast" + ".tar.gz "  + url;

//        return "wget -O hazelcast" + ".tar.gz " + url ;
    }

    public String extract() {
        return "tar -zxvf hazelcast" + ".tar.gz";
    }

    public String move(String original, String target ) {
        return "mv " + original + " " + target;
    }

    public String start(String configFile) {
        //java -cp "lib/*" -Dhazelcast.config=hazelcast-istanbul.xml com.hazelcast.core.server.StartServer & echo $!
        return "java -cp \"lib/*\" -Dhazelcast.config=" +
                configFile + " com.hazelcast.core.server.StartServer & echo $!";
    }

    public String startMC() {
        return "java -jar hazelcast/mancenter/mancenter.war";
    }
    public String upload(String user, String hostName, String nodeName, String configFile) {
        if(configFile == null){
            configFile = "bin/hazelcast.xml";
        }
        //scp bin/hazelcast.xml mefeakengin@localhost:~/hazelcast-istanbul.xml
        //While copying, put the configFile into the hazelcast installation path
        return "scp " + configFile + " " + user + "@" + hostName + ":~/" + "hazelcast-" + nodeName + ".xml";
    }

}
