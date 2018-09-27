package hsGlennLee.me.ProducerAndConsumer;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Hello world!
 *
 */
public class WordCountApp {
    public static void main( String[] args ) throws InterruptedException {
        ArrayBlockingQueue<Product> queue = new ArrayBlockingQueue<Product>(50);
        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue, counts));
        
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        
        producer.join();
        queue.put(new PoisonPill());
        consumer.join();
        
        long end = System.currentTimeMillis();
        System.out.println((end-start)+ "ms");
        System.out.println(counts);
    }
}
