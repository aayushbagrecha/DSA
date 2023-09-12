import student.TestCase;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HashTableTest extends TestCase {

    private HashTable ht;
    private Record record1;
    private Record record2;

    @Before
    public void setUp() {

        File file = new File("output.txt");
        PrintStream stream;
        try {
            stream = new PrintStream(file);
            System.setOut(stream);

            ht = new HashTable(64, 16);

            record1 = new Record(1, "Seminar 1", "2111011200", 60, (short)10,
                (short)20, 100, "Description 1", "Keyword1, Keyword2");
            record2 = new Record(2, "Seminar 2", "2111021300", 45, (short)15,
                (short)25, 75, "Description 2", "Keyword3, Keyword4");

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testInsertAndSearch() {
        assertTrue(ht.insert(record1));
        assertNotNull(ht.search(1));
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
        assertNotNull(ht.search(1));
        assertNotNull(ht.search(2));
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

        setUp();
        ht.insert(record1);
        ht.insert(record2);

        ht.printHashTable();

        String finalOutput = "";

        try {

            BufferedReader file = new BufferedReader(new FileReader(
                "output.txt"));
            String line;

            while ((line = file.readLine()) != null) {
                finalOutput += line;
            }

            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Record[] table = ht.getTable();

        String expectedOutput = "HashTable:" + "1:1" + "2:2"
            + "total records: 2";

        System.out.println(expectedOutput);
        System.out.println(finalOutput);

        assertEquals(expectedOutput, finalOutput);

    }
}
