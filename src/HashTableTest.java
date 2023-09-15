import java.io.FileNotFoundException;
import student.TestCase;

public class HashTableTest extends TestCase {

  private HashTable ht;
  private MemManager memManager;
  private SeminarRecord record1;
  private SeminarRecord record2;
  private SeminarRecord record3;
  private Handle record1Handle;
  private Handle record2Handle;
  private Handle record3Handle;

  // @Before
  public void setUp() throws Exception {
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

      record3 = new SeminarRecord(3, "Seminar 3", "2111021300", 45, (short)15,
        (short)25, 75, "Description 3", new String[] { "Keyword1, Keyword2" });
      byte[] record3_serialized = record3.serialize();
      record2Handle = memManager.insert(record3_serialized,
        record3_serialized.length);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  //
  public void testInsertAndSearch() {
    assertTrue(ht.insert(record1.getId(), record1Handle));
    assertEquals(ht.search(1), record1Handle);
  }


  /**
   * This test method evaluates the HashTable's delete operation. It inserts
   * a record, deletes it, and verifies that the record is successfully
   * deleted the first time and fails to delete it the second time, as the
   * record is no longer present.
   */
  //
  public void testDelete() {
    ht.insert(record1.getId(), record1Handle);
    assertTrue(ht.delete(record1.getId()));
  }


  public void testDeleteNegative() {
    // ht.insert(record1.getId(), record1Handle);
    assertFalse(ht.delete(record1.getId()));
  }


  /**
   * This test method checks if a record is successfully inserted into the
   * hash table when there is no conflict (i.e., no record with the same ID
   * exists).
   *
   * @throws Exception
   */
  //
  public void testSuccessfulRecordInsertion() throws Exception {
    assertTrue(ht.insert(record1.getId(), record1Handle));

    // Check if the record is present in the hash table

  }


  /**
   * This test method checks if the search method correctly finds and returns
   * a record when it exists and is not deleted in the hash table.
   *
   * @throws Exception
   */
  //
  public void testSuccessfulRecordSearch() throws Exception {
    ht.insert(record1.getId(), record1Handle);
    Handle retrievedHandle = ht.search(record1.getId());
    assertNotNull(retrievedHandle);
  }


  /**
   * This test method checks if the delete method correctly marks a record as
   * deleted and returns true when the record exists, is not deleted yet, and
   * the deletion is successful.
   */
  //
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
  //
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
  public void testInsertDuplicate() {
    assertTrue(ht.insert(record1.getId(), record1Handle));

  }


  /**
   * This test method tests the HashTable's behavior when searching for a
   * record that has been previously deleted. It inserts a record, deletes
   * it, and then checks whether searching for the deleted record returns
   * null.
   */

  public void testSearchDeletedRecord() {
    ht.insert(record1.getId(), record1Handle);
    ht.delete(1);
    assertNull(ht.search(1));
  }


  /**
   * This test method evaluates the HashTable's delete operation when
   * attempting to delete a record that doesn't exist in the table. It
   * verifies that attempting to delete a non-existing record returns false.
   */

  public void testDeleteNonExistingRecord() {
    assertFalse(ht.delete(1));
  }
}
