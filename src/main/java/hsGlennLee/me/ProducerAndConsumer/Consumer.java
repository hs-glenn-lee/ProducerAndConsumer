package hsGlennLee.me.ProducerAndConsumer;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

class Consumer implements Runnable{
	
	private BlockingQueue<Product> queue;
	HashMap<String, Integer> counts;
	
	Consumer (BlockingQueue<Product> queue, HashMap<String, Integer> counts) {
		this.queue = queue;
		this.counts = counts;
	}

	public void run() {
		try {
			while(true) {
				Product product = queue.take();
				System.out.println("consumed");
		        if (product.isPoisonPill())
		        	break;
		        Iterable<String> words = new Words(product.getContent());
		        for (String word: words)
		          countWord(word);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void countWord(String word) {
		Integer cnt = counts.get(word);
		if(cnt == null) {
			counts.put(word, new Integer(1));
		}else {
			counts.put(word, ++cnt);
		}
	}
}
