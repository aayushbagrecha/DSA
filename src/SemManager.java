import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SemManager {
    private MemManager memoryManager;
    private HashTable hashTable;

    public SemManager(int initialMemorySize, int initialHashSize) {
        memoryManager = new MemManager(initialMemorySize);
        hashTable = new HashTable(initialHashSize);
    }


    public static void main(String[] args) {
        // if (args.length != 3) {
        // System.err.println(
        // "Usage: java SemManager {initial-memory-size} {initial-hash-size}
        // {command-file}");
        // System.exit(1);
        // }

        // int initialMemorySize = Integer.parseInt(args[0]);
        // int initialHashSize = Integer.parseInt(args[1]);
        // String commandFile = args[2];

        int initialMemorySize = 64;
        int initialHashSize = 4;
        String commandFile = "input.txt";

        SemManager semManager = new SemManager(initialMemorySize,
            initialHashSize);
        semManager.processCommands(commandFile);
    }


    private void processCommands(String commandFile) {
        try (Scanner scanner = new Scanner(new File(commandFile))) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim().replaceAll("\\s+",
                    " ");

                // ArrayList<String> verbs = new ArrayList<>();

                // // Add some verbs to the ArrayList
                // verbs.add("insert");
                // verbs.add("delete");
                // verbs.add("search");
                // int id = -1;

                // if (verbs.contains(command.split("\\s")[0])) {
                // id = Integer.parseInt(command.split("\\s")[1]);
                // }

                // System.out.println(command);

                if (command.startsWith("insert")) {
                    int id = Integer.parseInt(command.split("\\s")[1]);
                    processInsertCommand(scanner, id);
                }
                else if (command.startsWith("delete")) {
                    int id = Integer.parseInt(command.split("\\s")[1]);
                    processDeleteCommand(scanner, id);
                }
                else if (command.startsWith("search")) {
                    int id = Integer.parseInt(command.split("\\s")[1]);
                    processSearchCommand(scanner, id);
                }
                else if (command.startsWith("print hashtable")) {
                    hashTable.printHashTable();
                }
                else if (command.startsWith("print blocks")) {
                    memoryManager.dump();
                }
                else {
                    // System.out.println("command not found");
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void processInsertCommand(Scanner scanner, int id) {
        String title = scanner.nextLine().trim();
        String dateLine = scanner.nextLine().trim().replaceAll("\\s+", " ");
        String dateTime = dateLine.split("\s")[0];
        int length = Integer.parseInt(dateLine.split("\s")[1]);
        short x = Short.parseShort(dateLine.split("\s")[2]);
        short y = Short.parseShort(dateLine.split("\s")[3]);
        int cost = Short.parseShort(dateLine.split("\s")[4]);
        String[] keywords = scanner.nextLine().trim().replaceAll("\\s+", " ")
            .split(" ");
        String description = scanner.nextLine().trim().replaceAll("\\s+", " ");

        // Convert keywords to ArrayList
        ArrayList<String> keywordList = new ArrayList<>();
        for (String keyword : keywords) {
            keywordList.add(keyword);
        }

        // Create a SeminarRecord
        SeminarRecord seminarRecord = new SeminarRecord(id, title, dateTime,
            length, x, y, cost, description, keywords);

        // Serialize the SeminarRecord and insert it into memory
        byte[] serializedRecord = seminarRecord.serialize();
        Handle handle = memoryManager.insert(serializedRecord,
            serializedRecord.length);

        // // Insert the handle into the hash table
        boolean insertStatus = hashTable.insert(id, handle);
        if (insertStatus) {
            System.out.println("Successfully inserted record with ID " + id);
            // System.out.println(seminarRecord.toString());
            // System.out.println(hashTable.getSize());
        }
        else
            System.out.println(
                "Insert FAILED - There is already a record with ID " + id);

    }


    private void processDeleteCommand(Scanner scanner, int id) {

        // Check if the key exists in the hash table
        Handle handle = hashTable.search(id);
        if (handle != null) {
            // Remove the record from the memory manager
            memoryManager.remove(handle);

            // Delete the entry from the hash table
            hashTable.delete(id);

            System.out.println("Record with ID " + id + " deleted");
        }
        else {
            System.out.println("Record with ID " + id + " not found");
        }
    }


    private void processSearchCommand(Scanner scanner, int id) {

        // Search for the record in the hash table
        Handle handle = hashTable.search(id);
        if (handle != null) {
            byte[] serializedRecord = new byte[handle.getRecordLength()];
            memoryManager.get(serializedRecord, handle,
                serializedRecord.length);
            SeminarRecord seminarRecord = SeminarRecord.deserialize(
                serializedRecord);
            System.out.println(seminarRecord.toString());
        }
        else {
            System.out.println("Search FAILED -- There is no record with ID "
                + id);
        }
    }
}
