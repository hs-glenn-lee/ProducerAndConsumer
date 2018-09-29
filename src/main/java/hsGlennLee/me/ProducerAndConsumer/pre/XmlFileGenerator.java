package hsGlennLee.me.ProducerAndConsumer.pre;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hsGlennLee.me.ProducerAndConsumer.Constants;


public class XmlFileGenerator {
	public static void main(String[] args) throws Exception {
		List<String> sourceWords = null;
		Path soruceWordsFilePath = Paths.get(Constants.sorceWordsPathString);
		sourceWords = Files.readAllLines(soruceWordsFilePath);
		

		Path targetDirectory = Paths.get(Constants.targetDirectoryPathString);
		if(!Files.exists(targetDirectory)) {
			Files.createDirectory(targetDirectory);
		}
		
		HashMap<String, Integer> stastics = new HashMap<String, Integer>();

		int sumByteSize = 0;
		int maxByteSize = 1024 * 1024 * 15; // 1 GB = 1024 * 1024 * 1024 * 1
		int fileCount = 0;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = null;
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		Random rand = new Random();
		
		while (sumByteSize < maxByteSize) {
			List<String> contentTextAsList = new ArrayList<String>();
			int randomnWordsSize = rand.nextInt(2000)+1; // 너무 많으면 stack over flow
			
			while(randomnWordsSize > 0) {
				contentTextAsList.add(sourceWords.get(rand.nextInt(sourceWords.size())));
				randomnWordsSize--;
			}
			
			doc = docBuilder.newDocument();
			Element parent = doc.createElement("root");
			doc.appendChild(parent);
			

			for(int i=0; i<contentTextAsList.size();) {
				int wordCntInElement = rand.nextInt(40)+1;
				int k = (i + wordCntInElement < contentTextAsList.size())? i + wordCntInElement : contentTextAsList.size();
				String elText = String.join("$", contentTextAsList.subList(i, k))+ "$";

				i = k;
				Element child = doc.createElement("word");
				child.setTextContent(elText);
				parent.appendChild(child);
				parent = child;
			}
			
			DOMSource source = new DOMSource(doc);
			Path filePath = Paths.get(targetDirectory.toString(), Integer.toString(fileCount)+".xml");

			StreamResult streamResult = new StreamResult(filePath.toFile());
			transformer.transform(source, streamResult);
			
			for(String word : contentTextAsList) {
				Integer cnt = stastics.get(word);
				if(cnt == null) {
					stastics.put(word, 1);
				}else {
					stastics.put(word, ++cnt);
				}
			}
			
			sumByteSize += Files.size(filePath);
			fileCount +=1;
		}
		
		
		
		Path stasticsFile = Paths.get(targetDirectory.toString(),"counts-result");
		Files.write(stasticsFile, stastics.toString().getBytes());
	}
}
