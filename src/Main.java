
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

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    /**
     * @param args
     */

    public static void main(String[] args) {
        int MEMORY_POOL_SIZE = Integer.parseInt(args[0]);
        int INITIAL_CAPACITY = Integer.parseInt(args[1]);
        String filename = args[2];// Pass the function a full filepath

        System.out.println(MEMORY_POOL_SIZE + INITIAL_CAPACITY + filename);

// String filename =
// "C:\\Users\\hp\\Downloads\\DSA-final-code\\DSA-final-code\\src\\input.txt";
// int MEMORY_POOL_SIZE = 64;
// int INITIAL_CAPACITY = 4;

        beginParsing(filename, MEMORY_POOL_SIZE, INITIAL_CAPACITY);// call the
                                                                   // parsing
                                                                   // function
    }


    public static void beginParsing(
        String filename,
        int MEMORY_POOL_SIZE,
        int INITIAL_CAPACITY) {
        try {

            String outputFile = "output.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile));

            HashTable ht = new HashTable(MEMORY_POOL_SIZE, INITIAL_CAPACITY,
                writer);
            Scanner lines = new Scanner(new File(filename));// Create our new
                                                            // scanner
            while (lines.hasNext()) {// While the scanner has information to
                                     // read
                String cmd = lines.nextLine().replaceAll("\\s+", " ").trim();
                ;// Read the next term

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
                        if (inserted == true) {
                            writer.println(
                                "Successfully inserted record with ID " + id);
                            writer.println("ID:" + id + ", Title: " + title);
                            writer.println("Date: " + date + ", Length: "
                                + length + ", X: " + x + ", Y: " + y
                                + ", Cost: " + cost);
                            writer.println("Description: " + description);
                            writer.println("Keywords: " + keywords);
                            // writer.println("Size: " +
                            // record.calculateSize());
                        }
                        else {
                            writer.println(
                                "Insert FAILED - There is already a record with ID "
                                    + id);
                        }
                        break;
                    case "search":// Found an search command
                        id = Integer.parseInt(cmd.split(" ")[1]);
                        record = ht.search(id);
                        if (record != null) {
                            writer.println("Found record with ID " + record.ID
                                + ":");
                            writer.println("ID:" + record.ID + ", Title: "
                                + record.Title);
                            writer.println("Date: " + record.Date + ", Length: "
                                + record.Length + ", X: " + record.X + ", Y: "
                                + record.Y + ", Cost: " + record.Cost);
                            writer.println("Description: "
                                + record.Description);
                            writer.println("Keywords: " + record.Keywords);
                        }
                        break;
                    case "delete":// Found a delete command
                        id = Integer.parseInt(cmd.split(" ")[1]);
                        boolean deletedStatus = ht.delete(id);
                        if (deletedStatus == true) {
                            writer.println("Record with ID " + id
                                + " successfully deleted from the database");
                        }
                        else {
                            writer.println(
                                "Delete FAILED -- There is no record with ID "
                                    + id);
                        }
                        break;
                    case "print":// Found a print command
                        String printCondition = cmd.split(" ")[1].replaceAll(
                            "\\s+", " ").trim();

                        if (printCondition.equals("blocks")) {
                            ht.printMemoryBlocks();
                        }
                        else {
                            ht.printHashTable();
                        }
                    default:// Found an unrecognized command
                        break;
                }
            }

            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*
 * - implement handle class in records
 * - code refactoring and documentation
 * - mutation testing
 * - Junit testing
 */
