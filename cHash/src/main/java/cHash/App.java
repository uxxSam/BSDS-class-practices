package cHash;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ConsistentHashRouter<VirtualNode> test = new  ConsistentHashRouter(new ArrayList<>(), 5);
        // test.addNode();
            // 32 300-400
    }
}
