package hsGlennLee.me.ProducerAndConsumer;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.ParserConfigurationException;

class Producer implements Runnable{
	
	private BlockingQueue<Product> queue;
	
	Producer (BlockingQueue<Product> queue) {
		this.queue = queue;
	}

	
	private class XmlIterator implements Iterator<Product> {
		private Parser parser;
		private File[] xmlFiles;
		private int idx=0;
		private XmlIterator (Path targetDir) throws ParserConfigurationException {
			parser = new Parser();
			xmlFiles = targetDir.toFile().listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if(name.contains(".xml")) {
						return true;
					}else {
						return false;
					}
				}
			});
		}
		
		public boolean hasNext() {
			if(idx < xmlFiles.length) {
				return true;
			}
			return false;
		}

		public Product next() {
			File nextFile = xmlFiles[idx];
			Product product = null;
			try {
				product = parser.parse(nextFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			idx++;
			return product;
		}
		
	}
	
	public void run() {
		Path targetPath = Paths.get(Constants.targetDirectoryPathString);
		Iterator<Product> products;
		try {
			products = new XmlIterator(targetPath);
			while(products.hasNext()) {
				queue.put(products.next());
				System.out.println("produced");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	
}
