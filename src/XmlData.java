import java.util.Map;
    public class XmlData {
        private String nodeName;
        private Map<String, String> attributes;

        public XmlData(String nodeName, Map<String, String> attributes) {
            this.nodeName = nodeName;
            this.attributes = attributes;
        }

        @Override
        public String toString() {
            return "NodeName: " + nodeName + ", Attributes: " + attributes;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }
