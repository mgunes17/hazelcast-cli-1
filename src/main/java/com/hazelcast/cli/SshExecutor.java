package com.hazelcast.cli;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class SshExecutor {

    public static String exec(String user, String host, int port, String command, boolean breakProcess, String identityPath, boolean shouldPrint) throws Exception {

//        System.out.println("user " + user + " host " + host + " port " + port + " identity path " + identityPath + " command " + command);
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
//            System.out.println("Connection situation : " + channel.isConnected());

            String out = "";
            while ((msg = in.readLine()) != null) {
                if (shouldPrint) {
                    System.out.println(msg);
                }
                out += msg;
                //How does the breakProcess work?
                //Is the first msg the PID? How do we make sure that?
                if (breakProcess) {
                    break;
                }
            }

            return out;

        } catch (JSchException e) {
            if (e.getMessage().equals("Auth fail")) {
                System.out.println("The authentication to the machine failed.");
            } else if (e.getMessage().contains("invalid privatekey")) {
                System.out.println("Invalid private key.");
            }
            if (e.getCause() != null) {
                if (e.getCause().getClass().getName().equals("java.io.FileNotFoundException")) {
                    System.out.println("Identity file \"" + identityPath + "\" not found");
                }
            }
            return "exception";
        } catch (Exception e) {
            System.out.println("Connection cannot be established. Please try again.");
            return "exception";
        } finally {
            if (channel != null)
                channel.disconnect();

            if (session != null)
                session.disconnect();
        }
    }

}
