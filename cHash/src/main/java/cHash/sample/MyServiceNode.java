package cHash.sample;

import cHash.ConsistentHashRouter;
import cHash.Node;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * a sample usage for routing a request to services based on requester ip
 */
public class MyServiceNode implements Node{
    private final String idc;
    private final String ip;
    private final int port;

    public MyServiceNode(String idc,String ip, int port) {
        this.idc = idc;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String getKey() {
        return idc + "-"+ip+":"+port;
    }

    @Override
    public String toString(){
        return getKey();
    }

    public static void main(String[] args) {
        //initialize 4 service node
        MyServiceNode node1 = new MyServiceNode("IDC1","127.0.0.1",8080);
        MyServiceNode node2 = new MyServiceNode("IDC1","127.0.0.1",8081);
        MyServiceNode node3 = new MyServiceNode("IDC1","127.0.0.1",8082);
        MyServiceNode node4 = new MyServiceNode("IDC1","127.0.0.1",8084);

        //hash them to hash ring
        ConsistentHashRouter<MyServiceNode> consistentHashRouter = new ConsistentHashRouter<>(Arrays.asList(node1,node2,node3,node4),10);//10 virtual node

        //we have 5 requester ip, we are trying them to route to one service node
        String requestIP1 = "192.168.0.1";
        String requestIP2 = "192.168.0.2";
        String requestIP3 = "192.168.0.3";
        String requestIP4 = "192.168.0.4";
        String requestIP5 = "192.168.0.5";
        String requestIP6 = "192.168.0.6";
        String requestIP7 = "192.168.0.7";
        String requestIP8 = "192.168.0.8";
        String requestIP9 = "192.168.0.9";
        String requestIP10 = "192.168.0.10";
        String requestIP11 = "192.168.0.11";
        String requestIP12 = "192.168.0.12";

        goRoute(consistentHashRouter,requestIP1,requestIP2,requestIP3,requestIP4,requestIP5,requestIP6,requestIP7,requestIP8,requestIP9,requestIP10,requestIP11,requestIP12);

        MyServiceNode node5 = new MyServiceNode("IDC2","127.0.0.1",8080);//put new service online
        System.out.println("-------------putting new node online " +node5.getKey()+"------------");
        consistentHashRouter.addNode(node5,10);
        // consistentHashRouter.showRing();

        goRoute(consistentHashRouter,requestIP1,requestIP2,requestIP3,requestIP4,requestIP5,requestIP6,requestIP7,requestIP8,requestIP9,requestIP10,requestIP11,requestIP12);

        consistentHashRouter.removeNode(node3);
        System.out.println("-------------remove node online " + node3.getKey() + "------------");
        goRoute(consistentHashRouter,requestIP1,requestIP2,requestIP3,requestIP4,requestIP5,requestIP6,requestIP7,requestIP8,requestIP9,requestIP10,requestIP11,requestIP12);
        consistentHashRouter.showRing();

        Map<String, Integer> distribution = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));

            String dest = consistentHashRouter.routeNode(generatedString).toString();

            distribution.put(dest, distribution.getOrDefault(dest, 0) + 1);
        }

        for (String dest : distribution.keySet()) {
            System.out.println("Physical Node: " + dest + ", Count: " + distribution.get(dest));
        }

    }

    private static void goRoute(ConsistentHashRouter<MyServiceNode> consistentHashRouter ,String ... requestIps){
        for (String requestIp: requestIps) {
            System.out.println(requestIp + " is route to " + consistentHashRouter.routeNode(requestIp));
        }
    }
}
