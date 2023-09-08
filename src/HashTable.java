import java.io.PrintWriter;
// import java.util.*;

public class HashTable {
    private static final double LOAD_FACTOR_THRESHOLD = 0.5;

    private Record[] table;
    private int size;
    private int memoryPoolSize;
    private int[] freeBlocks;
    private PrintWriter writer;

    public HashTable(
        int MEMORY_POOL_SIZE,
        int INITIAL_CAPACITY,
        PrintWriter writer) {
        table = new Record[INITIAL_CAPACITY];
        size = 0;
        memoryPoolSize = MEMORY_POOL_SIZE;
        freeBlocks = new int[MEMORY_POOL_SIZE];

        for (int i = 0; i < memoryPoolSize; i++) {
            freeBlocks[i] = -1;
        }

        this.writer = writer;
    }


    public boolean insert(Record record) {
        if (search(record.ID) != null) {
            // Record with the same ID already exists
            return false;
        }

        if (size >= table.length * LOAD_FACTOR_THRESHOLD) {
            expandTable();
        }
        int index = findIndex(record.ID);
        table[index] = record;
        size++;
        return true;
    }


    public Record search(int ID) {
        int index = findIndex(ID);
        if (table[index] != null && table[index].ID == ID
            && !table[index].deleted) {
            return table[index];
        }
        writer.println("Search FAILED -- There is no record with ID " + ID);
        return null;
    }


    public boolean delete(int ID) {
        int index = findIndex(ID);
        if (table[index] != null && table[index].ID == ID) {
            table[index].deleted = true; // Mark the record as deleted with a
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
            if (record != null && !record.deleted) {
                insert(record); // Reinsert non-deleted records
            }
        }
    }


    private int findIndex(int ID) {
        int index = ID % table.length; // 10 % 4
        int step = (((ID / table.length) % (table.length / 2)) * 2) + 1;

        while (table[index] != null && table[index].ID != ID) {
            index = (index + step) % table.length; // 4 % 4 = 0, 2 % 4
            // index = (((ID / table.length) % (table.length / 2)) * 2) + 1;
        }
        return index;
    }


    public void printHashTable() {

        writer.println("HashTable:");
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            Record record = table[i];
            if (record != null) {
                if (record.deleted) {
                    writer.println((i + ": TOMBSTONE"));
                }
                else {
                    writer.println((i + ": " + record.ID));
                    count++;
                }
            }
        }

        writer.println("total records: " + count);

    }


    public void printMemoryBlocks() {
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
