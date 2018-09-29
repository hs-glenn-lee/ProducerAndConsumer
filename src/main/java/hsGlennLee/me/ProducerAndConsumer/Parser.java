package hsGlennLee.me.ProducerAndConsumer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	
	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	StringBuilder contentSB = new StringBuilder();
	
	public Parser () throws ParserConfigurationException {
		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
	}
	
	public ParsedXML parse(File file) throws Exception {
		Document doc = dBuilder.parse(file);
		String contentText= doc.getFirstChild().getTextContent().trim();
		return new ParsedXML(contentText);
	}
}
