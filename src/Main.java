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
            File inputFile = new File("input (1).xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            List<XmlData> dataList = new ArrayList<>();
            Map<String, Integer> conBlock = new HashMap<>();
            processNode(doc.getDocumentElement(), "", dataList, new HashMap<>(), conBlock);

            for (String key : conBlock.keySet()) {
                System.out.println(key + ": " + conBlock.get(key));
            }

            for (int i = 0; i < conBlock.size(); i++) {
                Object o = conBlock.keySet().toArray()[i];
//                System.out.println(String.valueOf(o) + ": " + conBlock.get(i));
//                saveToContextIfNotNull(context, SVEDPREDDOC + "_" + (i), conBlock.get(i));
            }

            // Выводим содержимое списка
            for (XmlData data : dataList) {
                System.out.println(data.getNodeName());
                for (Map.Entry<String, String> entry : data.getAttributes().entrySet()) {
                    System.out.println("   " + data.getNodeName() +"_"+entry.getKey() + ": " + entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processNode(Node node, String path, List<XmlData> dataList, Map<String, Integer> nodeOccurrences, Map<String, Integer> conBlock) {
        final String SVEDPREDDOC = "Файл_Документ_СвЮЛ_СвЗапЕГРЮЛ_СведПредДок";
        final String EGRUL = "Файл_Документ_СвЮЛ_СвЗапЕГРЮЛ";
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
            if (node.getNodeName().equals("СвЗапЕГРЮЛ")) {
                conBlock.put(SVEDPREDDOC + "_" + nodeOccurrences.get(EGRUL), nodeOccurrences.get(SVEDPREDDOC));
                nodeOccurrences.put(SVEDPREDDOC, 0);
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
            if (node.getParentNode().getNodeName().equals("СведПредДок")) {
                attributes.put(node.getNodeName() + "_" + nodeOccurrences.get(EGRUL) + "_" + nodeOccurrences.get(newPathAttribute), node.getTextContent().trim());
            } else {
                attributes.put(node.getNodeName(), node.getTextContent().trim());
            }
            dataList.add(new XmlData(newPathAttribute, attributes));
        } else {
            dataList.add(new XmlData(newPath, attributes));
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                processNode(child, newPath, dataList, nodeOccurrences, conBlock);
            }
        }
    }
}
