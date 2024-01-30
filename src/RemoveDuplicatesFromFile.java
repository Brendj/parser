import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RemoveDuplicatesFromFile {

    public static void main(String[] args) {
        // Путь к текстовому файлу
        String filePath = "in.txt";

        // Создаем пустой набор для хранения уникальных слов
        Set<String> uniqueWords = new HashSet<>();

        try {
            // Создаем объект Scanner для чтения из файла
            Scanner scanner = new Scanner(new File(filePath));

            // Читаем слова из файла построчно
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Добавляем слово в набор уникальных слов
                uniqueWords.add(line);
            }

            // Закрываем объект Scanner
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            e.printStackTrace();
        }

        // Выводим уникальные слова в консоль
        for (String word : uniqueWords) {
            System.out.println(word);
        }
    }
}
