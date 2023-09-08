/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 *
 * @author Aayush Bagrecha
 * @author Yash Shrikant
 * @version 1.0
 */

// On my honor:
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

import java.io.PrintWriter;

public class HashTable {
    /**
     * The HashTable class is a data structure that allows for efficient
     * insertion,
     * deletion, and searching
     * of records based on their ID.
     */

    private static final double LOAD_FACTOR_THRESHOLD = 0.5;

    private Record[] table;
    private int size;
    private int memoryPoolSize;
    private int[] freeBlocks;
    private PrintWriter writer;

    // The code snippet is the constructor of the HashTable class. It
    // initializes the HashTable object
    // with the specified memory pool size, initial capacity, and PrintWriter
    // object.
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
     * The function inserts a record into a table if it doesn't already exist
     * and expands the table if
     * it reaches a load factor threshold.
     * 
     * @param record
     *            The "record" parameter is an object of type "Record" that
     *            represents the record to
     *            be inserted into the table.
     * @return The method is returning a boolean value. If the record with the
     *         same Id already exists
     *         in the table, it will return false. Otherwise, it will return
     *         true after inserting the record
     *         into the table.
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
     * The search function returns a record with a given ID if it exists in the
     * table, otherwise it
     * prints a message and returns null.
     * 
     * @param Id
     *            The Id parameter is an integer that represents the unique
     *            identifier of the record
     *            being searched for.
     * @param searchMode
     *            The searchMode parameter is a boolean value that determines
     *            whether or not to
     *            print a message if the search fails. If searchMode is true, a
     *            message will be printed. If
     *            searchMode is false, no message will be printed.
     * @return The method is returning a Record object if a record with the
     *         specified ID is found and
     *         is not marked as deleted. If no record is found or if the
     *         searchMode is true, it returns null.
     */
    public Record search(int id, boolean searchMode) {

        int index = findIndex(id);
        if (table[index] != null && table[index].getId() == id && !table[index]
            .getDeletedStatus()) {
            return table[index];
        }
        if (searchMode == true)
            writer.println("Search FAILED -- There is no record with ID " + id);
        return null;
    }


    /**
     * The function deletes a record with a given Id from a table by marking it
     * as deleted.
     * 
     * @param Id
     *            The "Id" parameter is an integer that represents the unique
     *            identifier of the record
     *            that needs to be deleted from the table.
     * @return The method is returning a boolean value. It returns true if the
     *         record with the given Id
     *         is found and successfully marked as deleted, and false otherwise.
     */
    public boolean delete(int id) {
        int index = findIndex(id);
        if (table[index] != null && table[index].getId() == id) {
            table[index].setDeletedStatus(true);
            ; // Mark the record as
              // deleted with a
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
            if (record != null && !record.getDeletedStatus()) {
                insert(record); // Reinsert non-deleted records
            }
        }
    }


    private int findIndex(int id) {
        int index = id % table.length; // 10 % 4
        int step = (((id / table.length) % (table.length / 2)) * 2) + 1;

        while (table[index] != null && table[index].getId() != id) {
            index = (index + step) % table.length; // 4 % 4 = 0, 2 % 4
            // index = (((Id / table.length) % (table.length / 2)) * 2) + 1;
        }
        return index;
    }


    /**
     * The function prints the contents of a hash table, including the index and
     * ID of each record, as
     * well as the total number of records.
     */
    public void printHashTable() {

        writer.println("HashTable:");
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            Record record = table[i];
            if (record != null) {
                if (record.getDeletedStatus()) {
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


    public void printMemoryBlocks() {
        /**
         * The function is commented out and does not perform any action.
         */
        // writer.println("FreeBlock List:");

        // for (int block : freeBlocks) {

        // if (block == -1)
        // continue;
        // else
        // writer.println(block + " ");
        // }

        // writer.println("There are no freeblocks in the memory pool");
    }
}
