
   import java.util.regex.*;
import java.nio.file.*;

    public class pasrs {
        public static void main(String[] args) {
            String inputFilePath = "in.txt";
            String outputFilePath = "out.txt";

            try {
                String input = new String(Files.readAllBytes(Paths.get(inputFilePath)));
                String output = removeOutsideTags(input);
                Files.write(Paths.get(outputFilePath), output.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static String removeOutsideTags(String input) {
            String tagStart = "<activiti:formProperty id=";
            String tagEnd = "</activiti:formProperty>";
            Pattern pattern = Pattern.compile(Pattern.quote(tagStart) + "(.*?)" + Pattern.quote(tagEnd), Pattern.DOTALL);
            Matcher matcher = pattern.matcher(input);

            StringBuilder output = new StringBuilder();
            while (matcher.find()) {
                output.append(matcher.group());
            }
            return output.toString();
        }
    }
