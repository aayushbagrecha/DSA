import java.io.PrintWriter;

public class HashTable {
    private static final double LOAD_FACTOR_THRESHOLD = 0.5;

    private Record[] table;
    private int size;
    private int memoryPoolSize;
    private int[] freeBlocks;
    private PrintWriter writer;

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


    public boolean insert(Record record) {
        if (search(record.Id, false) != null) {
            // Record with the same Id already exists
            return false;
        }

        if (size >= table.length * LOAD_FACTOR_THRESHOLD) {
            expandTable();
        }
        int index = findIndex(record.Id);
        table[index] = record;
        size++;
        return true;
    }


    public Record search(int Id, boolean searchMode) {
        int index = findIndex(Id);
        if (table[index] != null && table[index].Id == Id
            && !table[index].deleted) {
            return table[index];
        }
        if (searchMode == true)
            writer.println("Search FAILED -- There is no record with ID " + Id);
        return null;
    }


    public boolean delete(int Id) {
        int index = findIndex(Id);
        if (table[index] != null && table[index].Id == Id) {
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


    private int findIndex(int Id) {
        int index = Id % table.length; // 10 % 4
        int step = (((Id / table.length) % (table.length / 2)) * 2) + 1;

        while (table[index] != null && table[index].Id != Id) {
            index = (index + step) % table.length; // 4 % 4 = 0, 2 % 4
            // index = (((Id / table.length) % (table.length / 2)) * 2) + 1;
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
                    writer.println((i + ": " + record.Id));
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
