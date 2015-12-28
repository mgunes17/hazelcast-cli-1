package com.hazelcast.cli;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by emrah on 12/06/15.
 */
public class SshExecutor {

    public static String exec(String user, String host, int port, String command, boolean breakProcess) throws Exception {

        System.out.println("user " + user + " host " + host + " port " + port + " command " + command);
        Session session = null;
        ChannelExec channel = null;

        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();
            jsch.addIdentity("~/.ssh/id_rsa");
            jsch.setConfig(config);
//            jsch.setKnownHosts("~/.ssh/known_hosts");

            session = jsch.getSession(user, host, port);

            session.setConfig(config);


//            //Password and fingerprint will be given via UserInfo interface.
//            UserInfo ui = new UserInfoImpl(pwd, fingerPrint);
//            session.setUserInfo(ui);

            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            //Executes the command given
            channel.setCommand(command);
            channel.connect();

            String msg = null;
            String out = null;
            while ((msg = in.readLine()) != null) {
                out = msg;
                //How does the breakProcess work?
                //Is the first msg the PID? How do we make sure that?
                if (breakProcess) {
                    break;
                }
            }
            return out;

        } catch (Exception e) {
            throw e;
        } finally {
            if (channel != null)
            channel.disconnect();
            if (session != null)
            session.disconnect();
        }
    }

}
