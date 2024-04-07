import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PopularNamesExtractor {

    public static void main(String[] args) {
        String inputFile = "Popular_Baby_Names_NY.xml";
        String outputFile = "Selected_Names.xml";
        String ethnicity = "WHITE NON HISPANIC"; 
        int numberOfNames = 10; 

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(inputFile));

            NodeList nodeList = doc.getElementsByTagName("row");
            List<NameInfo> nameList = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String ethcty = element.getElementsByTagName("ethcty").item(0).getTextContent();
                if (ethcty.equals(ethnicity)) {
                    String name = element.getElementsByTagName("nm").item(0).getTextContent();
                    String gender = element.getElementsByTagName("gndr").item(0).getTextContent();
                    int count = Integer.parseInt(element.getElementsByTagName("cnt").item(0).getTextContent());
                    int rank = Integer.parseInt(element.getElementsByTagName("rnk").item(0).getTextContent());

                    nameList.add(new NameInfo(name, gender, count, rank));
                }
            }

            Collections.sort(nameList, Comparator.comparingInt(NameInfo::getRank));

            Document newDoc = builder.newDocument();
            Element rootElement = newDoc.createElement("SelectedNames");
            newDoc.appendChild(rootElement);

            for (int i = 0; i < Math.min(numberOfNames, nameList.size()); i++) {
                NameInfo nameInfo = nameList.get(i);
                Element nameElement = newDoc.createElement("Name");
                nameElement.setAttribute("Name", nameInfo.getName());
                nameElement.setAttribute("Gender", nameInfo.getGender());
                nameElement.setAttribute("Count", String.valueOf(nameInfo.getCount()));
                nameElement.setAttribute("Rank", String.valueOf(nameInfo.getRank()));
                rootElement.appendChild(nameElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(new File(outputFile));
            transformer.transform(source, result);

            System.out.println("Selected names saved to " + outputFile);
        } catch (ParserConfigurationException | TransformerException | org.xml.sax.SAXException | java.io.IOException e) {
            e.printStackTrace();
        }
    }

    static class NameInfo {
        private final String name;
        private final String gender;
        private final int count;
        private final int rank;

        public NameInfo(String name, String gender, int count, int rank) {
            this.name = name;
            this.gender = gender;
            this.count = count;
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public int getCount() {
            return count;
        }

        public int getRank() {
            return rank;
        }
    }
}
