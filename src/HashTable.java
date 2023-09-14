public class HashTable {
    private Entry[] table;
    private int capacity;
    private int size;
    private static final double loadFactor = 0.5;

    public HashTable(int initialCapacity) {
        capacity = initialCapacity;
        size = 0;
        table = new Entry[capacity];
    }


    public boolean insert(int key, Handle value) {
        if (size >= table.length * loadFactor) {
            // Resize the table if load factor is exceeded
            resize();
        }

        int index = find(key); // find whether the element already exists or not
                               // return -1 if it doesnt
                               // return actual index if it does

        if (index == -1) { // this indicates that the element is not present
            // Insert the key-value pair
            index = findEmptySlot(key);
            table[index] = new Entry(key, value);
            // System.out.println(table[index].value);
            size++;
            return true;
        }
        else { // this indicates that the element is already present in the
               // table
            return false;
        }
    }


    public void delete(int key) {
        int index = find(key);

        if (index != -1) {
            // Mark the entry as a tombstone
            table[index].isTombstone = true;
            size--;
        }
    }


    public Handle search(int key) {
        int index = find(key);

        if (index == -1) // element is not found in the hash table
            return null;
        else {
            return table[index].value;
        }
    }


    public void printHashTable() {
        System.out.println("HashTable: ");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                if (table[i].isTombstone)
                    System.out.println(i + ": TOMBSTONE");
                else
                    System.out.println(i + ": " + table[i].key);
            }
        }
        System.out.println("Total records: " + size);
    }


    private int find(int key) {
        int index = hash(key);

        // check if the calc index contains any value
        // if it is null then it is a new element which needs to be inserted
        while (table[index] != null) {
            // check if the element at the index is the key which we are finding
            if (table[index].key == key && !table[index].isTombstone) {
                return index;
            }
            index = (index + 1) % capacity;
        }

        return -1; // Key not found
    }


    public int getSize() {
        return capacity;
    }


    private int findEmptySlot(int key) {
        int index = hash(key);
        int step = (((key / capacity) % (capacity / 2)) * 2) + 1;

        while (table[index] != null && table[index].key != key
            && !table[index].isTombstone) {
            index = (index + step) % capacity;
        }
        return index;
    }


    private int hash(int key) {
        return key % capacity;
    }


    private void resize() {
        int newCapacity = capacity * 2;
        Entry[] newTable = new Entry[newCapacity];

        System.out.println("Hash table expanded to " + newCapacity
            + " records");

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !table[i].isTombstone) {
                int newIndex = hash(table[i].key); // Use the hash function for
                                                   // indexing
                while (newTable[newIndex] != null) {
                    newIndex = (newIndex + 1) % newCapacity;
                }
                newTable[newIndex] = table[i];
            }
        }

        table = newTable;
        capacity = newCapacity;
        // printHashTable();
    }

    private class Entry {
        int key;
        Handle value;
        boolean isTombstone;

        Entry(int key, Handle value) {
            this.key = key;
            this.value = value;
            this.isTombstone = false;
        }
    }
}
