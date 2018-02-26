import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class WriteXMLFile {

    //    <?xml version="1.0" encoding="UTF-8" standalone="no" ?>
//<company>
//	<staff id="1">
//		<firstname>yong</firstname>
//    <lastname>mook kim</lastname>
//		<nickname>mkyong</nickname>
//		<salary>100000</salary>
//	</staff>
//</company>
    public static void main(String argv[]) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("company");
            doc.appendChild(rootElement);

            // staff elements
//            Element staff = doc.createElement("Staff");
//            rootElement.appendChild(staff);
//
//            // set attribute to staff element
//            Attr attr = doc.createAttribute("id");
//            attr.setValue("1");
//            staff.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");

            // firstname elements
            Element firstname = doc.createElement("firstname");
            firstname.appendChild(doc.createTextNode("yong"));
            rootElement.appendChild(firstname);

            // lastname elements
            Element lastname = doc.createElement("lastname");
            lastname.appendChild(doc.createTextNode("mook kim"));
            rootElement.appendChild(lastname);

            // nickname elements
            Element nickname = doc.createElement("nickname");
            nickname.appendChild(doc.createTextNode("mkyong"));
            rootElement.appendChild(nickname);

            // salary elements
            Element salary = doc.createElement("salary");
            salary.appendChild(doc.createTextNode("100000"));
            rootElement.appendChild(salary);

            // salary elements
            Element salary1 = doc.createElement("salary");
            salary1.appendChild(doc.createTextNode("100000"));
            rootElement.appendChild(salary1);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File("/home/nihla/IdeaProjects/adaptive-pipeline/test.xml"));

            // Output to console for testing
            StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);
//
//            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
