import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HashTableTest {
    public static void main(String[] args) {
        String fileName = "output.txt"; // Replace with your file path

        String content;

        try {
            // Read the file contents into a string
            content = Files.readString(Paths.get(fileName),
                StandardCharsets.UTF_8);

            // Print the content to the console
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String expectedOutput = "HashTable:\n" + //
            "1: 1\n" + //
            "2: 2";

        assertTrue(expectedOutput, content);
    }
}
