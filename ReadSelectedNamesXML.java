import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadSelectedNamesXML {

    public static void main(String[] args) {
        String inputFile = "Selected_Names.xml";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(inputFile));

            NodeList nameList = doc.getElementsByTagName("Name");

            System.out.println("Selected Names:");
            for (int i = 0; i < nameList.getLength(); i++) {
                Element nameElement = (Element) nameList.item(i);
                String name = nameElement.getAttribute("Name");
                String gender = nameElement.getAttribute("Gender");
                int count = Integer.parseInt(nameElement.getAttribute("Count"));
                int rank = Integer.parseInt(nameElement.getAttribute("Rank"));

                System.out.println("Name: " + name + ", Gender: " + gender + ", Count: " + count + ", Rank: " + rank);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

