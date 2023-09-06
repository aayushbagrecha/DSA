import java.util.Arrays;

public class HashTable {

        private int size;
        private int capacity;
        private Seminar[] table;

        public HashTable(int initialCapacity) {
            this.capacity = initialCapacity;
            this.size = 0;
            this.table = new Seminar[capacity];
            //Arrays.fill(table, null);
        }

        // Hash function 1
        private int hash1(String key) {
            return key.hashCode() % capacity;
        }

        // Hash function 2 for double hashing
        private int hash2(String key) {
            return 7 - (key.hashCode() % 7);
        }

        // Insert a key-value pair into the hash table
        public void insert(Seminar record) {
            if (size >= capacity * 0.5) {
                expand(); // Expand the hash table if the load factor is too high
            }

            int index = hash1(record.id);
            int step = hash2(key);

            while (table[index] != null) {
                index = (index + step) % capacity;
            }

            table[index] = key;
            size++;
        }

        // Delete a key from the hash table
        public void delete(String key) {
            int index = search(key);

            if (index != -1) {
                table[index] = null;
                size--;
                System.out.println("Deleted key: " + key);
            } else {
                System.out.println("Key not found: " + key);
            }
        }

        // Search for a key in the hash table
        public int search(String key) {
            int index = hash1(key);
            int step = hash2(key);

            while (table[index] != null) {
                if (table[index].equals(key)) {
                    return index;
                }
                index = (index + step) % capacity;
            }

            return -1; // Key not found
        }

        // Print the contents of the hash table
        public void printHashTable() {
            System.out.println("Hash Table:");
            for (int i = 0; i < capacity; i++) {
                if (table[i] != null) {
                    System.out.println("Index " + i + ": " + table[i]);
                }
            }
        }

        // Expand the hash table and rehash all elements
        private void expand() {
            int newCapacity = capacity * 2;
            String[] newTable = new String[newCapacity];
            Arrays.fill(newTable, null);

            for (String key : table) {
                if (key != null) {
                    int index = key.hashCode() % newCapacity;
                    int step = hash2(key);

                    while (newTable[index] != null) {
                        index = (index + step) % newCapacity;
                    }

                    newTable[index] = key;
                }
            }

            table = newTable;
            capacity = newCapacity;
        }
}
