
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class SemManager{
    
    

    static HashTable hashTable; // Declare a hash table instance

    public static void main(String[] args) {
        if (args.length != 1) {
//            System.err.println("Usage: java SemManager <input_file>");
            System.exit(1);
        }

        String filename = args[0];
        hashTable = new HashTable(Integer.parseInt(args[1])); // Initialize the hash table
        startParsing(filename); // Call the parsing function
    }
//
//    static void insert(int id, String title, String date, int length, int x, int y, int cost,
//            String keywords, String description) {
//
//        String key = title; // You can use the title as the key for insertion
//        hashTable.insert(key); // Insert the key into the hash table
//
//        // You can store the additional data associated with the key in your data structure here
//        // For now, we'll just print the data
//        System.out.printf("id: %s ;title: %s ;date: %s ;length: %s ;x: %s ;y: %s ;cost: %s ;\nkeywords: %s ;\ndescription: %s",
//                id, title,date,length, x, y, cost, keywords, description);
//    }
//
//    static void search() {
//        // Implement your search logic here using the hash table
//        System.out.println("hello from search");
//    }
//
//    static void delete() {
//        // Implement your delete logic here using the hash table
//        System.out.println("hello from delete");
//    }
//
//    static void print() {
//        // Implement your print logic here using the hash table
//        System.out.println("hello from print");
//    }

    public static void startParsing(String filename) {
        try {
            Scanner lines = new Scanner(new File(filename)); // Create our new scanner
            while (lines.hasNext()) {// While the scanner has information to read
                String cmd = lines.nextLine().replaceAll("\\s+", " ").trim();

                String verb = cmd.split("\\s")[0];

                switch (verb) {
                    case "insert":
                        int id = Integer.parseInt(cmd.split("\\s")[1]);
                        String title = lines.nextLine();
                        String dateField = lines.nextLine().replaceAll("\\s+", " ").trim();
                        String date = dateField.split("\\s")[0];
                        int length = Integer.parseInt(dateField.split("\\s")[1]);
                        int x = Integer.parseInt(dateField.split("\\s")[2]);
                        int y = Integer.parseInt(dateField.split("\\s")[3]);
                        int cost = Integer.parseInt(dateField.split("\\s")[4]);
                        String keywords = lines.nextLine().replaceAll("\\s+", " ").trim().replaceAll("\\s", ",\\s");
                        String description = lines.nextLine().replaceAll("\\s+", " ").trim();
                        insert(id, title, date, length, x, y, cost, keywords, description);
                        break;
                    case "search":// Found a search command
                        search();
                        break;
                    case "delete":// Found a delete command
                        delete();
                        break;
                    case "print":// Found a print command
                        print();
                        break;
                    default:// Found an unrecognized command
                        System.out.println(cmd);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//public class SemManager {
//
//  /**
//   * @param args
//   */
//  
//  private static ExtensibleHashTable hashTable;
//  
//  public static void main(String[] args) {
//      String filename = args[2];
//      hashTable = new ExtensibleHashTable(10); // Initialize the hash table
//      startParsing(filename);// call the parsing function
//  }
//
//  static void insert(int id, String title, String date, int length, int x, int y, int cost,
//          String keywords, String description) {
//
//      System.out.printf(
//              "id: %s ;title: %s ;date: %s ;length: %s ;x: %s ;y: %s ;cost: %s ;\nkeywords: %s ;\ndescription: %s",
//              id,
//              title,
//              date,
//              length, x, y, cost, keywords, description);
//  }
//
//  static void search() {
//      System.out.println("hello from search");
//  }
//
//  static void delete() {
//      System.out.println("hello from delete");
//  }
//
//  static void print() {
//      System.out.println("hello from print");
//  }
//
//  public static void startParsing(String filename) {
//      try {
//          Scanner lines = new Scanner(new File(filename));// Create our new scanner
//          while (lines.hasNext()) {// While the scanner has information to read
//              String cmd = lines.nextLine().replaceAll("\\s+", " ").trim();
//              ;// Read the next term
//
//              String verb = cmd.split("\\s")[0];
//
//              switch (verb) {
//                  case "insert":
//                      int id = Integer.parseInt(cmd.split("\\s")[1]);
//                      String title = lines.nextLine();
//                      String dateField = lines.nextLine().replaceAll("\\s+", " ").trim();
//                      String date = dateField.split("\\s")[0];
//                      int length = Integer.parseInt(dateField.split("\\s")[1]);
//                      int x = Integer.parseInt(dateField.split("\\s")[2]);
//                      int y = Integer.parseInt(dateField.split("\\s")[3]);
//                      int cost = Integer.parseInt(dateField.split("\\s")[4]);
//                      String keywords = lines.nextLine().replaceAll("\\s+", " ").trim().replaceAll("\\s", ",\\s");
//                      String description = lines.nextLine().replaceAll("\\s+", " ").trim();
//                      insert(id, title, date, length, x, y, cost, keywords, description);
//                      break;
//                  case "search":// Found an search command
//                      search();
//                      break;
//                  case "delete":// Found a delete command
//                      delete();
//                      break;
//                  case "print":// Found a print command
//                      print();
//                      break;
//                  default:// Found an unrecognized command
//                      System.out.println(cmd);
//                      break;
//              }
//          }
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//  }
//
//}