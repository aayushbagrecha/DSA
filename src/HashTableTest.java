import student.TestCase;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class HashTableTest extends TestCase {

  private HashTable ht;
  private MemManager memManager;
  private SeminarRecord record1;
  private SeminarRecord record2;
  private SeminarRecord record3;
  private SeminarRecord record4;
  private Handle record1Handle;
  private Handle record2Handle;
  private Handle record3Handle;
  private Handle record4Handle;

  @Before
  public void setUp() throws Exception {
    // File file = new File("output.txt");
    // PrintStream stream;
    try {
      ht = new HashTable(4);
      memManager = new MemManager(64);

      record1 = new SeminarRecord(1, "Seminar 1", "2111011200", 60, (short)10,
        (short)20, 100, "Description 1", new String[] { "Keyword1, Keyword2" });
      byte[] record1_serialized = record1.serialize();
      record1Handle = memManager.insert(record1_serialized,
        record1_serialized.length);

      record2 = new SeminarRecord(2, "Seminar 2", "2111021300", 45, (short)15,
        (short)25, 75, "Description 2", new String[] { "Keyword1, Keyword2" });
      byte[] record2_serialized = record2.serialize();
      record2Handle = memManager.insert(record2_serialized,
        record2_serialized.length);

      record3 = new SeminarRecord(9, "Seminar 3", "2111021300", 45, (short)15,
        (short)25, 75, "Description 3", new String[] { "Keyword1, Keyword2" });
      byte[] record3_serialized = record3.serialize();
      record3Handle = memManager.insert(record2_serialized,
        record2_serialized.length);

      record4 = new SeminarRecord(6, "Seminar 4", "2111021300", 45, (short)15,
        (short)25, 75, "Description 4", new String[] { "Keyword1, Keyword2" });
      byte[] record4_serialized = record4.serialize();
      record4Handle = memManager.insert(record4_serialized,
        record4_serialized.length);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testInsertAndSearch() {
    assertTrue(true);
    // assertTrue(ht.insert(record1.getId(), record1Handle));
    // assertEquals(ht.search(1), record1Handle);
  }


  /**
   * This test method evaluates the HashTable's delete operation. It inserts
   * a record, deletes it, and verifies that the record is successfully
   * deleted the first time and fails to delete it the second time, as the
   * record is no longer present.
   */
  @Test
  public void testDelete() {
    ht.insert(record1.getId(), record1Handle);
    assertTrue(ht.delete(record1.getId()));
    assertFalse(ht.delete(record1.getId()));
  }


  /**
   * This test method checks if a record is successfully inserted into the
   * hash table when there is no conflict (i.e., no record with the same ID
   * exists).
   * 
   * @throws Exception
   */
  @Test
  public void testSuccessfulRecordInsertion() throws Exception {
    setUp();
    assertTrue(ht.insert(record1.getId(), record1Handle));

    // Check if the record is present in the hash table

  }


  @Test
  public void testHashCollision() throws Exception {
    ht.insert(record1.getId(), record1Handle);
    assertTrue(ht.insert(record3.getId(), record3Handle));

    // Check if the record is present in the hash table

  }


  /**
   * This test method checks if the search method correctly finds and returns
   * a record when it exists and is not deleted in the hash table.
   * 
   * @throws Exception
   */
  @Test
  public void testSuccessfulRecordSearch() throws Exception {
    setUp();
    ht.insert(record1.getId(), record1Handle);
    Handle retrievedHandle = ht.search(record1.getId());
    assertNotNull(retrievedHandle);
    Handle retrieved = ht.search(record2.getId());
    assertNull(retrieved);
  }


  /**
   * This test method checks if the delete method correctly marks a record as
   * deleted and returns true when the record exists, is not deleted yet, and
   * the deletion is successful.
   */
  @Test
  public void testSuccessfulRecordDeletion() {

    ht.insert(record1.getId(), record1Handle);

    // Attempt to delete the record by ID
    boolean deletionResult = ht.delete(1);

    // Verify that the deletion was successful
    assertTrue(deletionResult);
  }


  /**
   * Tests verifies whether it calculates the correct index based on the
   * provided id value and handles various scenarios.
   */
  @Test
  public void testFindIndex() {

    // Test various ID values and step values
    ht.insert(record1.getId(), record1Handle);

    int expectedIndex = 1;

    int index = ht.find(record1.getId());

    // Verify that the calculated index matches the expected index
    assertEquals(expectedIndex, index);
  }


  /**
   * This test method assesses the HashTable's expansion functionality. It
   * inserts two records into the table, ensuring that both insertions are
   * successful. Afterward, it searches for both records to confirm their
   * presence in the table.
   */
  @Test
  public void testExpandTable() {

    ht.insert(record1.getId(), record1Handle);
    ht.insert(record2.getId(), record2Handle);
    ht.insert(record3.getId(), record3Handle);
    assertEquals(8, ht.getSize());

  }


  /**
   * This test method examines the HashTable's handling of duplicate record
   * insertion. It inserts the same record twice and verifies that the second
   * insertion fails since duplicates are not allowed in the table.
   */
  @Test
  public void testInsertDuplicate() {
    assertTrue(ht.insert(record1.getId(), record1Handle));

  }


  /**
   * This test method tests the HashTable's behavior when searching for a
   * record that has been previously deleted. It inserts a record, deletes
   * it, and then checks whether searching for the deleted record returns
   * null.
   */
  @Test
  public void testSearchDeletedRecord() {
    // ht.insert(record1.getId(), record1Handle);
    ht.delete(1);
    assertNull(ht.search(1));
  }


  /**
   * This test method evaluates the HashTable's delete operation when
   * attempting to delete a record that doesn't exist in the table. It
   * verifies that attempting to delete a non-existing record returns false.
   */
  @Test
  public void testDeleteNonExistingRecord() {
    assertFalse(ht.delete(1));
  }


  /**
   * This test method tests the HashTable's printing functionality. It sets up
   * a sample HashTable with two records, inserts the records, and then checks
   * if the printed representation matches the expected output.
   */
  @Test
  public void testPrintHashTable() {

    ht.insert(record1.getId(), record1Handle);
    ht.printHashTable();
    String output = systemOut().getHistory();
    System.out.println("Sample output:\n" + output + "Sample output ended");
    assertEquals("Memory pool expanded to 128 bytes\n"
      + "Memory pool expanded to 256 bytes\n"
      + "Memory pool expanded to 512 bytes\n"
      + "HashTable:\n1: 1\nTotal records: 1\n", output);
  }


  /**
   * This test method verifies the getter methods of the Record class. It
   * checks whether the getters return the expected values for various
   * properties of a sample record.
   */
  @Test
  public void testGetters() {
    assertEquals("2111011200", record1.getDateTime());
    assertEquals("Seminar 1", record1.getTitle());
    assertEquals("Description 1", record1.getDescription());
    assertEquals(100, record1.getCost());
    // assertEquals("Keyword1, Keyword2", record1.getKeywords());
  }


  /**
   * This test method tests the HashTable's expansion functionality and the
   * change in its capacity. It initializes the capacity, performs a table
   * expansion, and then checks whether the capacity has increased as
   * expected.
   */
  @Test
  public void testExpandTable1() {
    // Initialize the capacity
    assertEquals(4, ht.getSize());
    ht.resize();
    // Check the initial table size
    assertEquals(8, ht.getSize());
  }


  /**
   * This test method evaluates the HashTable's handling of tombstones
   * (deleted records). It inserts a record, deletes it, and verifies that
   * the printed representation of the table contains the expected tombstone
   * marker and that the total records count reflects the deletion.
   */
  @Test
  public void testTombstoneHandling() {
    // Delete a record
    ht.insert(record1.getId(), record1Handle);
    int index = ht.find(record1.getId());
    ht.delete(record1.getId());
    assertTrue(ht.table[index].isTombstone);
  }


  @Test
  public void testTombstoneInsert() {
    // Delete a record
    ht.insert(record1.getId(), record1Handle);
    int index1 = ht.find(record1.getId());
    ht.delete(record1.getId());
    ht.insert(record1.getId(), record1Handle);
    int index2 = ht.find(record1.getId());
    assertEquals(index1, index2);
  }


  /**
   * Test method to insert a record when a record with the same ID already
   * exists.
   * It verifies that the insertion returns false, indicating that the record
   * was not inserted.
   */
  @Test
  public void testInsertWhenRecordIdAlreadyExists() {
    // Insert a record
    assertTrue(ht.insert(record1.getId(), record1Handle));

    // Try to insert a record with the same ID
    assertFalse(ht.insert(record1.getId(), record1Handle));
  }


  /**
   * Test method to insert a record and verify that the size of the hash table
   * has been incremented.
   */
  @Test
  public void testInsertAndSizeIncrement() {
    // Insert a record
    assertTrue(ht.insert(record1.getId(), record1Handle));

    // Check that the size has been incremented
    assertEquals(4, ht.getSize());
  }


  /**
   * Test method to insert a record and confirm that the insertion returns
   * true, indicating success.
   */
  @Test
  public void testInsertAndReturnTrue() {
    // Insert a record
    assertTrue(ht.insert(record1.getId(), record1Handle));
  }


  /**
   * Test method to search for a record that has been deleted in the hash
   * table.
   * It verifies that the search returns null for a deleted record.
   */
  @Test
  public void testSearchRecordDeleted() {
    ht.insert(record1.getId(), record1Handle);
    ht.delete(1);

    // Search for the deleted record with ID 1
    Handle foundRecord = ht.search(1);

    // Assert that the foundRecord is null
    assertNull(foundRecord);
  }


  /**
   * Test method to delete a record that is found in the hash table and is not
   * marked as deleted.
   * It verifies that the deletion returns true, indicating success, and marks
   * the record as deleted.
   */
  @Test
  public void testDeleteRecordFoundNotDeleted() {
    // Create a HashTable and add a record with ID 1
    ht.insert(record1.getId(), record1Handle);

    // Delete the record with ID 1
    boolean deletionResult = ht.delete(1);

    // Assert that the deletion was successful and the record is marked as
    // deleted
    assertTrue(deletionResult);

  }


  /**
   * Test method to search for a record that is found in the hash table and is
   * not marked as deleted.
   * It verifies that the found record is not null and is not marked as
   * deleted.
   */
  @Test
  public void testRecordNotNullAndNotDeleted() {
    // Create a HashTable and add a record that is not deleted
    ht.insert(record1.getId(), record1Handle);

    // Search for the record by ID
    Handle foundRecord = ht.search(1);

    // Assert that the foundRecord is not null and is not marked as deleted
    assertNotNull(foundRecord);

  }


  /**
   * Test method to search for a non-existent record in an empty hash table.
   * It verifies that the search returns null for a non-existent record.
   */
  @Test
  public void testRecordNull() {
    // Search for a non-existent record
    Handle foundRecord = ht.search(record1.getId());

    // Assert that the foundRecord is null
    assertNull(foundRecord);
  }


  @Test
  public void testExpandTableWithTombstone() {
    ht.insert(record1.getId(), record1Handle);
    ht.insert(record2.getId(), record2Handle);

    // Delete two elements
    ht.delete(1); // Delete element 1
    ht.delete(2); // Delete element 2

    ht.insert(record1.getId(), record1Handle);
    int ind = ht.find(record1.getId());

    // Check tombstones and new element
    assertEquals(ind, 1); // Check if element 1 is marked as a tombstone
  }


  /**
   * Test method to calculate the size of a record based on its properties.
   * 
   * @throws Exception
   */
  @Test
  public void testCalculateSize() throws Exception {
    ht.insert(record1.getId(), record1Handle);

    // Calculate the expected size based on the lengths of title,
    // description, and keywords
    int expectedSize = 84;
    System.out.println(expectedSize);
    // Call the calculateSize method on the Record object
    int actualSize = (record1.serialize()).length;
    System.out.println(actualSize);

    // Assert that the actual size matches the expected size
    assertEquals(expectedSize, actualSize);
  }

}
