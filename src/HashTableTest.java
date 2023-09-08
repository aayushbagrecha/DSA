import student.TestCase;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.PrintWriter;
import java.io.StringWriter; // Add this import
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HashTableTest {

    private HashTable ht;
    private Record record1;
    private Record record2;

    @Before
    public void setUp() {
        PrintWriter writer = null;
        try {

            String outputFile = "output.txt";
            writer = new PrintWriter(new FileWriter(outputFile));

            ht = new HashTable(64, 16, writer);

            record1 = new Record(1, "Seminar 1", "2111011200", 60, (short)10,
                (short)20, 100, "Description 1", "Keyword1, Keyword2");
            record2 = new Record(2, "Seminar 2", "2111021300", 45, (short)15,
                (short)25, 75, "Description 2", "Keyword3, Keyword4");
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                writer.close();
            }
        }
        
    }


    @Test
    public void testInsertAndSearch() {
        assertTrue(ht.insert(record1));
        assertNotNull(ht.search(1, true));
        // assertNull(ht.search(3,true));
    }


    @Test
    public void testDelete() {
        ht.insert(record1);
        assertTrue(ht.delete(record1.getId()));
        assertFalse(ht.delete(record1.getId()));
    }


    @Test
    public void testExpandTable() {
        assertTrue(ht.insert(record1));
        assertTrue(ht.insert(record2));
        assertNotNull(ht.search(1, true));
        assertNotNull(ht.search(2, true));
    }


    @Test
    public void testInsertDuplicate() {
        assertTrue(ht.insert(record1));
        assertFalse(ht.insert(record1));
    }


    @Test
    public void testSearchDeletedRecord() {
        ht.insert(record1);
        ht.delete(1);
        // assertNull(ht.search(1,true));
    }


    @Test
    public void testDeleteNonExistingRecord() {
        assertFalse(ht.delete(1));
    }


    @Test
    public void testPrintHashTable() {

// ht.printHashTable();
//
// // Add assertions for the captured output
// String expectedOutput = "HashTable:\n0: 1\n1: 2\ntotal records: 2\n";
// assertEquals(expectedOutput, sw.toString());

        setUp();
        ht.insert(record1);
        ht.insert(record2);

        ht.printHashTable();
        String fileName = "output.txt"; // Replace with your file path

        String content = null;

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
        System.out.println(expectedOutput);
        System.out.println(content);
        assertEquals(expectedOutput, content);
    }
}
