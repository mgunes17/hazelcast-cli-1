package com.hazelcast.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalExecutor {
	private static Logger logger = LoggerFactory.getLogger(LocalExecutor.class);
	
    public static String exec(String command, boolean breakProcess) throws Exception {

        String[] commandSplit = command.split(" ");
        ProcessBuilder pb = new ProcessBuilder(commandSplit);
        pb.redirectErrorStream(true);

        System.out.println("Linux command: " + command);

        String response = null;

        try {
            Process shell = pb.start();
            shell.waitFor();

//            if () {
//
// To capture output from the shell
            java.io.InputStream shellIn = shell.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(shellIn));

            String msg = null;
            String out = null;
            while ((msg = in.readLine()) != null) {
                out = msg;
                System.out.println("Message: " + msg);
                //How does the breakProcess work?
                //Is the first msg the PID? How do we make sure that?
                if (breakProcess) {
                    break;
                }
            }

// Wait for the shell to finish and get the return code
                int shellExitStatus = shell.waitFor();
                System.out.println("Exit status" + shellExitStatus);

//                StringWriter writer = new StringWriter();
//                IOUtils.copy(inputStream, writer, encoding);
//                String theString = writer.toString();
//
//                response = java.io.streaconvertStreamToStr(shellIn);

                shellIn.close();
//            }

        } catch (Exception e) {
        	logger.warn("Linux command running is unsuccessfull", e);
            System.out.println("Error occured while executing Linux command. Error Description: "
                    + e.getMessage());
        }

        return response;



//        StringBuffer output = new StringBuffer();
//        final Process p;
//        try {
//            System.out.println("Creating a new process ... " + command);
//
//            p = Runtime.getRuntime().exec("mkdir /Users/mefeakengin/hazelcast/ahmet");
//            p.waitFor();
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//            String msg = null;
//            String out = null;
//            while ((msg = in.readLine()) != null) {
//                out = msg;
//                //How does the breakProcess work?
//                //Is the first msg the PID? How do we make sure that?
//                if (breakProcess) {
//                    break;
//                }
//            }
//            return out;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return output.toString();
    }

}


//public class ShellTest {
//    public static void main(String[] args) throws java.io.IOException, java.lang.InterruptedException {
//        // Create ProcessBuilder instance for UNIX command ls -l
//        java.lang.ProcessBuilder processBuilder = new java.lang.ProcessBuilder("ls", "-l");
//        // Create an environment (shell variables)
//        java.util.Map env = processBuilder.environment();
//        env.clear();
//        env.put("COLUMNS", "3"); // See manpage ls(1)
//        // You can change the working directory
//        pb.directory(new java.io.File("/Users"));
//        // Start new process
//        java.lang.Process p = pb.start();
//    }
//}
//
//
//public class LinuxInteractor {
//
//    public static String executeCommand(String command, boolean waitForResponse) {
//
//        String response = "";
//
//        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
//        pb.redirectErrorStream(true);
//
//        System.out.println("Linux command: " + command);
//
//        try {
//            Process shell = pb.start();
//
//            if (waitForResponse) {
//
//// To capture output from the shell
//                InputStream shellIn = shell.getInputStream();
//
//// Wait for the shell to finish and get the return code
//                int shellExitStatus = shell.waitFor();
//                System.out.println("Exit status" + shellExitStatus);
//
//                response = convertStreamToStr(shellIn);
//
//                shellIn.close();
//            }
//
//        } catch (IOException e) {
//            System.out.println("Error occured while executing Linux command. Error Description: "
//                    + e.getMessage());
//        } catch (InterruptedException e) {
//            System.out.println("Error occured while executing Linux command. Error Description: "
//                    + e.getMessage());
//        }
//
//        return response;
//    }
//}