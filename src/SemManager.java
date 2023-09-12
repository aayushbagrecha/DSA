import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * The class containing the main method.
 *
 * This class reads commands from a file, performs operations on a hash table
 * based on the commands, and writes the results to an output file.
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
public class SemManager {
    /**
     * The main function takes command line arguments for memory pool size,
     * initial capacity, and filename, and then calls the beginParsing function
     * with those arguments.
     *
     * @param args
     *            Command line arguments: memory pool size, initial capacity,
     *            and filename.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) {
        String filename = "input.txt";
        int memoryPoolSize = 64;
        int initialCapacity = 4;

        // int memoryPoolSize = Integer.parseInt(args[0]);
        // int initialCapacity = Integer.parseInt(args[1]);
        // String filename = args[2];

        beginParsing(filename, memoryPoolSize, initialCapacity);
    }


    /**
     * The beginParsing function reads commands from a file, performs operations
     * on a hash table
     * based on the commands, and writes the results to an output file.
     *
     * @param filename
     *            The name of the file that contains the commands to be parsed.
     * @param memoryPoolSize
     *            The size of the memory pool that will be used by the HashTable
     *            object.
     * @param initialCapacity
     *            The initial size of the hash table.
     */
    public static void beginParsing(
        String filename,
        int memoryPoolSize,
        int initialCapacity) {
        try {

            File file = new File("./output.txt");
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);

            HashTable ht = new HashTable(memoryPoolSize, initialCapacity);
            Scanner lines = new Scanner(new File(filename));

            while (lines.hasNext()) {
                String cmd = lines.nextLine().replaceAll("\\s+", " ").trim();
                String verb = cmd.split("\\s")[0];

                switch (verb) {
                    case "insert":
                        int id = Integer.parseInt(cmd.split(" ")[1]);
                        String title = lines.nextLine();
                        String dateField = lines.nextLine().replaceAll("\\s+",
                            " ").trim();
                        String date = dateField.split(" ")[0];
                        int length = Integer.parseInt(dateField.split(" ")[1]);
                        short x = Short.parseShort(dateField.split(" ")[2]);
                        short y = Short.parseShort(dateField.split(" ")[3]);
                        int cost = Integer.parseInt(dateField.split(" ")[4]);
                        String keywords = lines.nextLine().replaceAll("\\s+",
                            " ").trim().replaceAll(" ", ", ");
                        String description = lines.nextLine().replaceAll("\\s+",
                            " ").trim();
                        Record record = new Record(id, title, date, length, x,
                            y, cost, description, keywords);
                        boolean inserted = ht.insert(record);

                        if (inserted) {
                            System.out.println(
                                "Successfully inserted record with ID " + id);
                            System.out.println("ID: " + id + ", Title: "
                                + title);
                            System.out.println("Date: " + date + ", Length: "
                                + length + ", X: " + x + ", Y: " + y
                                + ", Cost: " + cost);
                            System.out.println("Description: " + description);
                            System.out.println("Keywords: " + keywords);
                        }
                        else {
                            System.out.println(
                                "Insert FAILED - There is already a record "
                                    + "with ID " + id);
                        }
                        break;

                    case "search":
                        id = Integer.parseInt(cmd.split(" ")[1]);
                        Record searchedRecord = ht.search(id);

                        if (searchedRecord != null) {
                            System.out.println("Found record with ID "
                                + searchedRecord.getId() + ":");
                            System.out.println("ID: " + searchedRecord.getId()
                                + ", Title: " + searchedRecord.getTitle());
                            System.out.println("Date: " + searchedRecord
                                .getDate() + ", Length: " + searchedRecord
                                    .getLength() + ", X: " + searchedRecord
                                        .getX() + ", Y: " + searchedRecord
                                            .getY() + ", Cost: "
                                + searchedRecord.getCost());
                            System.out.println("Description: " + searchedRecord
                                .getDescription());
                            System.out.println("Keywords: " + searchedRecord
                                .getKeywords());
                        }
                        else
                            System.out.println(
                                "Search FAILED -- There is no record with ID "
                                    + id);
                        break;

                    case "delete":
                        id = Integer.parseInt(cmd.split(" ")[1]);
                        boolean deletedStatus = ht.delete(id);

                        if (deletedStatus) {
                            System.out.println("Record with ID " + id
                                + " successfully deleted from the database");
                        }
                        else {
                            System.out.println(
                                "Delete FAILED -- There is no record with ID "
                                    + id);
                        }
                        break;

                    case "print":
                        String printCondition = cmd.split(" ")[1].replaceAll(
                            "\\s+", " ").trim();

                        if (printCondition.equals("blocks")) {
                            // ht.printMemoryBlocks();
                        }
                        else {
                            ht.printHashTable();
                        }
                        break;

                    default:
                        break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
 * - implement handle class
 * - implement memoryPool class
 * - modify hashTable (in the context of handles and memoryPool)
 * - implement memoryManager (handles both hashtables and memory Pool)
 * -- implement freesize block
 * -- print out sizes after each insert command
 * - modify seminarManager with memManager
 * - mutation testing
 * - Junit testing
 * - change the code for searching in main.java to return output and write it
 * from there instead of writing it to file from hashtable itself
 */

// TESTS
/*
 * whether the command line arguements are being taken
 * whether all cases of insert, search, print and delete are being taken from
 * the input file
 * getting date, title, description, cost and keywords from the record
 * expanding the table
 * whether or not the output is going to file for search
 * finding index of given number
 * testing if tombstone is coming
 */
