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

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Запись данных в файл
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            processNode(doc.getDocumentElement(), writer, "");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processNode(Node node, BufferedWriter writer, String path) throws IOException {
        Map<String, String> attributes = new HashMap<>();
        for (int i = 0; i < node.getAttributes().getLength(); i++) {
            attributes.put(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getNodeValue());
        }

        String newPath = path.isEmpty() ? node.getNodeName() : path + "_" + node.getNodeName();
        writer.write(newPath + "\n");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            writer.write("\t" + newPath + "_" + entry.getKey() + ": " + entry.getValue() + "\n");
        }

        // Вывод данных в консоль
        System.out.println(newPath);
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            System.out.println("\t" + newPath + "_" + entry.getKey() + ": " + entry.getValue());
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                processNode(child, writer, newPath);
            }
        }
    }

}
