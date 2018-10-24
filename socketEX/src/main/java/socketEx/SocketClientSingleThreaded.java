package socketEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SocketClientSingleThreaded {
    
    public static void main(String[] args, CyclicBarrier barrier)  {
        String hostName;
        int port;
        
        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName= null;
            port = 12031;
        }
        long clientID = Thread.currentThread().getId();
        try {
            for (int i = 0; i < 100; i++) {
                Socket s = new Socket(hostName, port);
                PrintWriter out =
                    new PrintWriter(s.getOutputStream(), true);
                BufferedReader in =
                    new BufferedReader(
                        new InputStreamReader(s.getInputStream()));
                out.println("Client ID is " +  Long.toString(clientID));

                System.out.println(in.readLine());
                s.close();
            }
         
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }

        try {
            barrier.await();
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException ee) {

        }
    }
}
