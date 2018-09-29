package hsGlennLee.me.ProducerAndConsumer;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

public class ParserTest {
	public static void main(String[] args ) throws Exception  {
		File f = Paths.get("/1/0.xml").toFile();
		Parser parser = new Parser();
		ParsedXML parsed = parser.parse(f);
		
		
	}
}
