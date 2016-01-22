package com.hazelcast.cli;

import com.jcraft.jsch.*;

import java.io.*;
import java.nio.channels.Channel;
import java.util.Properties;

/**
 * Created by emrah on 12/06/15.
 */
public class SshExecutor {

    public static String exec(String user, String host, int port, String command, boolean breakProcess, String identityPath) throws Exception {

        System.out.println("user " + user + " host " + host + " port " + port + " identity path " + identityPath + " command " + command);
        Session session = null;
        ChannelExec channel = null;
        String msg = null;

        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            jsch.addIdentity(identityPath);
            jsch.setConfig(config);
            session = jsch.getSession(user, host, port);
            session.setTimeout(10000);
            session.setConfig(config);
            session.connect();

            int lineCounter = 0;

            channel = (ChannelExec) session.openChannel("exec");
            channel.setPty(false);
            BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            //Executes the command given
            ((ChannelExec) channel).setCommand(command);
            channel.connect();

            String out = null;
            while ((msg = in.readLine()) != null) {
                System.out.println(msg);
                out = msg;
                //How does the breakProcess work?
                //Is the first msg the PID? How do we make sure that?
                if (breakProcess) {
                    break;
                }
            }

            return msg;

        } catch (FileNotFoundException e) {
            System.out.println("Identity file " + identityPath +  " not found");
            return "exception";
        } catch (Exception e) {
            e.printStackTrace();
            return "exception";
        } finally {
            if (channel != null)
            channel.disconnect();

            if (session != null)
            session.disconnect();
        }
    }

}
