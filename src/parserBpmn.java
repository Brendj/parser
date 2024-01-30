import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class parserBpmn {
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            processNode(doc.getDocumentElement(), writer, "");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processNode(Node node, BufferedWriter writer, String indent) throws IOException {
        Map<String, String> attributes = new HashMap<>();
        for (int i = 0; i < node.getAttributes().getLength(); i++) {
            attributes.put(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getNodeValue());
        }

        writer.write(indent + "<activiti:formProperty id=\"=" + node.getNodeName() + "\" name=\"" + node.getNodeName() + "\" type=\"string\" writable=\"false\"></activiti:formProperty>\n");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            writer.write(indent + "\t<activiti:formProperty id=\"" + entry.getKey() + "\" name=\"" + entry.getKey() + "\" type=\"string\" variable=\"" + entry.getKey() + "\" writable=\"false\"></activiti:formProperty>: " + entry.getValue() + "\n");
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                processNode(child, writer, indent + "\t");
            }
        }
    }
}
