public class HashTable {
    public Entry[] table;
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


    public boolean delete(int key) {
        int index = find(key);

        if (index != -1) {
            // Mark the entry as a tombstone
            table[index].isTombstone = true;
            size--;
            return true;
        }
        return false;
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
        String output = "HashTable:\n";
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                if (table[i].isTombstone)
                    output += i + ": TOMBSTONE\n";
                else
                    output += i + ": " + table[i].key + "\n";
            }
        }
        System.out.println(output + "Total records: " + size);
    }


    public int find(int key) {
        int index = hash(key);
        int step = (((key / capacity) % (capacity / 2)) * 2) + 1; // the entire
                                                                  // point of
                                                                  // hash table
                                                                  // is that you
                                                                  // dont have
                                                                  // to probe
                                                                  // linearly -
                                                                  // probe in
                                                                  // steps of
                                                                  // the ID
        int initialIndex = index;

        while (table[index] != null) {
            if (table[index].key == key && !table[index].isTombstone) {
                return index;
            }
            index = (index + step) % capacity;

            // If we've looped back to the initial index, stop searching.
            if (index == initialIndex) {
                break;
            }
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


    public void resize() {
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

    public class Entry {
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
