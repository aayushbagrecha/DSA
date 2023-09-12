import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

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

    /**
     * Constructs a new `HashTable` object with the specified memory pool size,
     * initial capacity, and PrintWriter object.
     *
     * @param memoryPoolSize
     *            The size of the memory pool in bytes.
     * @param initialCapacity
     *            The initial capacity of the hash table.
     */
    public HashTable(int memoryPoolSize, int initialCapacity) {
        table = new Record[initialCapacity];
        size = 0;
        this.memoryPoolSize = memoryPoolSize;
        freeBlocks = new int[memoryPoolSize];

        for (int i = 0; i < memoryPoolSize; i++) {
            freeBlocks[i] = -1;
        }

        File file = new File("output.txt");
        PrintStream stream;
        try {
            stream = new PrintStream(file);
            System.setOut(stream);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        if (search(record.getId()) != null) {
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
     * The function returns an array of Record objects.
     * 
     * @return An array of type Record is being returned.
     */
    public Record[] getTable() {
        return table;
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
    public Record search(int id) {
        int index = findIndex(id);
        if (table[index] != null && table[index].getId() == id && !table[index]
            .isDeleted()) {
            return table[index];
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
        if (table[index] != null && table[index].getId() == id && !table[index]
            .isDeleted()) {
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

        System.out.println("Hash table expanded to " + table.length
            + " records");

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
     * The function prints the contents of a hash table, including the index,
     * record ID, and total
     * number of records.
     * 
     * @return The method is returning a string representation of the hash
     *         table, including the index
     *         and ID of each record in the table, as well as the total number
     *         of records.
     */
    public void printHashTable() {
        // // return table;
        System.out.println("HashTable:");
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            Record record = table[i]; // this is to prevent duplicate use of
            // table[i]
            if (record != null) {
                if (record.isDeleted()) {
                    System.out.println(i + ": TOMBSTONE");
                }
                else {
                    System.out.println(i + ": " + record.getId());
                    count++;
                }
            }
        }

        System.out.println("total records: " + count);
    }
}
