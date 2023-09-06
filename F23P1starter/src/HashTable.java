//import java.util.Arrays;

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
        private int hash1(int key) {
            return key % capacity;
        }

        // Hash function 2 for double hashing
        private int hash2(int key) {
            return (((key/capacity)%(capacity/2))*2)+1;//put 2nd formula (done)
        }

        // Insert a key-value pair into the hash table
        public boolean insert(Seminar record) {
            if (size >= capacity * 0.5) {
                expand(); // Expand the hash table if the load factor is too high
            }

            int index = hash1(record.id);
            int step = hash2(record.id);
       
            while (table[index] != null) {
                index = (index + step) % capacity;
            }
// add condition for duplicacy
            table[index] = record;
            size++;
            return true;
        }

        // Delete a key from the hash table
        public boolean delete(int id) {
            int index = search(id);

            if (index != -1) {
                table[index] = null;
                size--;
                System.out.println("Deleted key: " + id);
                return true;
            } else {
             
                System.out.println("Key not found: " + id);
                return false;
            }
        }

        // Search for a key in the hash table
        public int search(int record) {
            int index = hash1(record);
            int step = hash2(record);

            while (table[index] != null) {
                if (table[index].equals(record)) {
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
            Seminar[] newTable = new Seminar[newCapacity];
            //Arrays.fill(newTable, null);

            for (Seminar rec : table) {
                if (rec != null) {
                    int index = rec.id % newCapacity;
                    int step = hash2(rec.id);

                    while (newTable[index] != null) {
                        index = (index + step) % newCapacity;
                    }

                    newTable[index] = rec;
                }
            }

            table = newTable;
            capacity = newCapacity;
        }
}
