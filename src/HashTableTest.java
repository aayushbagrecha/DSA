import student.TestCase;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.PrintWriter;
import java.io.StringWriter; // Add this import

public class HashTableTest {

    private HashTable ht;
    private Record record1;
    private Record record2;

    @Before
    public void setUp() {
        ht = new HashTable(16, 4, null);

        record1 = new Record(1, "Seminar 1", "2111011200", 60, (short)10,
            (short)20, 100, "Description 1", "Keyword1, Keyword2");
        record2 = new Record(2, "Seminar 2", "2111021300", 45, (short)15,
            (short)25, 75, "Description 2", "Keyword3, Keyword4");
    }


    @Test
    public void testInsertAndSearch() {
        assertTrue(ht.insert(record1));
        assertNotNull(ht.search(1));
        assertNull(ht.search(3));
    }


    @Test
    public void testDelete() {
        ht.insert(record1);
        assertTrue(ht.delete(1));
        assertFalse(ht.delete(1));
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
        assertNull(ht.search(1));
    }


    @Test
    public void testDeleteNonExistingRecord() {
        assertFalse(ht.delete(1));
    }


    @Test
    public void testPrintHashTable() {
        ht.insert(record1);
        ht.insert(record2);

        // Replace "null" with a StringWriter for capturing output
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        ht = new HashTable(16, 4, writer);

        ht.printHashTable();

        // Add assertions for the captured output
        String expectedOutput = "HashTable:\n0: 1\n1: 2\ntotal records: 2\n";
        assertEquals(expectedOutput, sw.toString());
    }
}
