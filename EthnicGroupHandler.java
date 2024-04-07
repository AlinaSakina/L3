import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class EthnicGroupHandler extends DefaultHandler {
    
    private boolean inEthnicityTag = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("ethcty")) {
            inEthnicityTag = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inEthnicityTag) {
            String ethnicity = new String(ch, start, length);
            System.out.println("Ethnic group: " + ethnicity);
            inEthnicityTag = false;
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("ethcty")) {
            inEthnicityTag = false;
        }
    }
}



