package socketEx;

/**
 *
 * @author Ian Gortan
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientMultithreaded {
    
    static CyclicBarrier barrier;

    public static void main(String[] args)  {
        String hostName;
        final int MAX_THREADS = 1000;
        int port;

        hostName = null;
        port = 12031;
        barrier = new CyclicBarrier(MAX_THREADS + 1);

        // TO DO create and start client threads

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                public void run() {
                    SocketClientSingleThreaded.main(new String[]{ hostName, String.valueOf(port) }, barrier);
                }
            };

            thread.start();
        }


        try {
            barrier.await();
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException ee) {

        }
    }

      
}
