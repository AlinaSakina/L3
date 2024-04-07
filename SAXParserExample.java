import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserExample extends DefaultHandler {

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        System.out.println("Element: " + qName);
    }

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            String xmlFile = "Popular_Baby_Names_NY.xml";

            saxParser.parse(xmlFile, new SAXParserExample());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
