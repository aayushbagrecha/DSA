import java.io.PrintWriter;

/**
 * The `HashTable` class represents a data structure that allows for efficient
 * insertion, deletion, and searching of records based on their ID.
 *
 * On my honor:
 * - I have not used source code obtained from another current or
 * former student, or any other unauthorized source, either
 * modified or unmodified.
 * - All source code and documentation used in my program is
 * either my original work, or was derived by me from the
 * source code published in the textbook for this course.
 * - I have not discussed coding details about this project with
 * anyone other than my partner (in the case of a joint
 * submission), instructor, ACM/UPE tutors, or the TAs assigned
 * to this course. I understand that I may discuss the concepts
 * of this program with other students, and that another student
 * may help me debug my program so long as neither of us writes
 * anything during the discussion or modifies any computer file
 * during the discussion. I have violated neither the spirit nor
 * letter of this restriction.
 *
 * @author Aayush Bagrecha
 * @author Yash Shrikant
 * @version 1.0
 */
public class HashTable {

    private static final double LOAD_FACTOR_THRESHOLD = 0.5;

    private Record[] table;
    private int size;
    private int memoryPoolSize;
    private int[] freeBlocks;
    private PrintWriter writer;

    /**
     * Constructs a new `HashTable` object with the specified memory pool size,
     * initial capacity, and PrintWriter object.
     *
     * @param memoryPoolSize
     *            The size of the memory pool in bytes.
     * @param initialCapacity
     *            The initial capacity of the hash table.
     * @param writer
     *            The PrintWriter object used for output.
     */
    public HashTable(
        int memoryPoolSize,
        int initialCapacity,
        PrintWriter writer) {
        table = new Record[initialCapacity];
        size = 0;
        this.memoryPoolSize = memoryPoolSize;
        freeBlocks = new int[memoryPoolSize];

        for (int i = 0; i < memoryPoolSize; i++) {
            freeBlocks[i] = -1;
        }

        this.writer = writer;
    }


    /**
     * Inserts a record into the table if it doesn't already exist and expands
     * the table if it reaches a load factor threshold.
     *
     * @param record
     *            The record to be inserted into the table.
     * @return `true` if the record is inserted successfully, `false` if a
     *         record with the same ID already exists.
     */
    public boolean insert(Record record) {
        if (search(record.getId(), false) != null) {
            // Record with the same id already exists
            return false;
        }

        if (size >= table.length * LOAD_FACTOR_THRESHOLD) {
            expandTable();
        }
        int index = findIndex(record.getId());
        table[index] = record;
        size++;
        return true;
    }


    /**
     * Searches for a record with a given ID in the table.
     *
     * @param id
     *            The ID of the record to search for.
     * @param searchMode
     *            `true` to print a message if the search fails,
     *            `false` otherwise.
     * @return The found record if not deleted, `null` if not found or marked
     *         as deleted.
     */
    public Record search(int id, boolean searchMode) {
        int index = findIndex(id);
        if (table[index] != null && table[index].getId() == id && !table[index]
            .isDeleted()) {
            return table[index];
        }
        if (searchMode == true) {
            writer.println("Search FAILED -- There is no record with ID " + id);
        }
        return null;
    }


    /**
     * Deletes a record with a given ID by marking it as deleted.
     *
     * @param id
     *            The ID of the record to be deleted.
     * @return `true` if the record is found and successfully marked as deleted,
     *         `false` otherwise.
     */
    public boolean delete(int id) {
        int index = findIndex(id);
        if (table[index] != null && table[index].getId() == id) {
            table[index].setDeleted(true); // Mark the record as deleted with a
                                           // tombstone
            size--;
            return true;
        }
        return false;
    }


    private void expandTable() {
        Record[] oldTable = table;
        table = new Record[2 * oldTable.length];
        size = 0;

        writer.println("Hash table expanded to " + table.length + " records");

        for (Record record : oldTable) {
            if (record != null && !record.isDeleted()) {
                insert(record); // Reinsert non-deleted records
            }
        }
    }


    private int findIndex(int id) {
        int index = id % table.length;
        int step = (((id / table.length) % (table.length / 2)) * 2) + 1;

        while (table[index] != null && table[index].getId() != id) {
            index = (index + step) % table.length;
        }
        return index;
    }


    /**
     * Prints the contents of the hash table, including the index and ID of
     */
    public void printHashTable() {
        writer.println("HashTable:");
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            Record record = table[i];
            if (record != null) {
                if (record.isDeleted()) {
                    writer.println((i + ": TOMBSTONE"));
                }
                else {
                    writer.println((i + ": " + record.getId()));
                    count++;
                }
            }
        }
        writer.println("total records: " + count);
    }


    /**
     * Prints the freeblocks of the hash table
     */
    public void printMemoryBlocks() {
        writer.println("FreeBlock List:");
        for (int block : freeBlocks) {
            if (block == -1)
                continue;
            else
                writer.println(block + " ");
        }
        writer.println("There are no freeblocks in the memory pool");
    }
}
