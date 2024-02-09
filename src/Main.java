import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            List<XmlData> dataList = new ArrayList<>();
            processNode(doc.getDocumentElement(), "", dataList, new HashMap<>(), new HashMap<>());

            // Выводим содержимое списка
            for (XmlData data : dataList) {
                System.out.println(data.getNodeName());
                for (Map.Entry<String, String> entry : data.getAttributes().entrySet()) {
                    System.out.println("   " + entry.getKey() + ": " + entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processNode(Node node, String path, List<XmlData> dataList, Map<String, Integer> nodeOccurrences, Map<String, Integer> nodeBlock) {
        String newPath = path.isEmpty() ? node.getNodeName() : path + "_" + node.getNodeName();
        String newPathAttribute = path.isEmpty() ? node.getNodeName() : path;

        Map<String, String> attributes = new HashMap<>();
        List<String> nodeList = new ArrayList<>();
        nodeList.add("Файл_Документ_СвЮЛ_СвУчредит_УчрРФСубМО_СвОргОсущПр");
        nodeList.add("Файл_Документ_СвЮЛ_СвУчредит_УчрРФСубМО_СвОргОсущПр_ГРНДатаПерв");
        nodeList.add("Файл_Документ_СвЮЛ_СвУчредит_УчрРФСубМО_СвОргОсущПр_НаимИННЮЛ");
        nodeList.add("Файл_Документ_СвЮЛ_СвУчредит_УчрРФСубМО_СвОргОсущПр_НаимИННЮЛ_ГРНДата");
        nodeList.add("Файл_Документ_СвЮЛ_СвОКВЭД_СвОКВЭДДоп_ГРНДата");
        nodeList.add("Файл_Документ_СвЮЛ_СвЗапЕГРЮЛ_ВидЗап");
        nodeList.add("Файл_Документ_СвЮЛ_СвЗапЕГРЮЛ_СвРегОрг");
        nodeList.add("Файл_Документ_СвЮЛ_СвОКВЭД_СвОКВЭДДоп");
        nodeList.add("Файл_Документ_СвЮЛ_СвЗапЕГРЮЛ");
        nodeList.add("Файл_Документ_СвЮЛ_СвЗапЕГРЮЛ_СведПредДок");

        if (nodeOccurrences.containsKey(newPath)) {
            int count = nodeOccurrences.get(newPath);
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                attributes.put(node.getAttributes().item(i).getNodeName() + "_" + (count + 1), node.getAttributes().item(i).getNodeValue());
            }
            nodeOccurrences.put(path + "_" + node.getNodeName(), count + 1);
        } else {
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                if (nodeList.contains(newPath)) {
                    attributes.put(node.getAttributes().item(i).getNodeName()+"_1", node.getAttributes().item(i).getNodeValue());
                } else {
                    attributes.put(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getNodeValue());
                }
            }
            nodeOccurrences.put(path + "_" + node.getNodeName(), 1);
        }

        if (node.getTextContent() != null && !node.getTextContent().trim().isEmpty() && attributes.isEmpty()) {
            System.out.println(node.getParentNode().getNodeName());
            int count = 0;
            if (node.getParentNode().getNodeName().equals("СвЗапЕГРЮЛ")) {
                count = 0;
                nodeBlock.clear();
            } else if (node.getParentNode().equals("СведПредДок")) {
                nodeBlock.put("СведПредДок", count + 1);
                attributes.put(node.getNodeName() + "_" + nodeBlock.get(node.getNodeName()), node.getTextContent().trim());
            } else {
                attributes.put(node.getNodeName()+"_test", node.getTextContent().trim());
            }
            dataList.add(new XmlData(newPathAttribute, attributes));
        } else {
            dataList.add(new XmlData(newPath, attributes));
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                processNode(child, newPath, dataList, nodeOccurrences, nodeBlock);
            }
        }
    }
}
