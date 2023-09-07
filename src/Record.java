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

/**
 * The `Record` class represents a record with various properties such as ID,
 * title, date, length,
 * cost, coordinates, description, keywords, size, and deletion status.
 */
public class Record {
    /**
     * fields of the record class which will be accessed
     */
    public int id;
    public String title;
    public String date;
    public int length, cost;
    public short x, y;
    public String description, keywords;
    public int size;
    public boolean deleted;

    /**
     * The code you provided is a constructor for the `Record` class. A
     * constructor is a special method
     * that is used to initialize the object of a class.
     */
    public Record(
        int id,
        String title,
        String date,
        int length,
        short x,
        short y,
        int cost,
        String description,
        String keywords) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.length = length;
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.description = description;
        this.keywords = keywords;
        this.size = calculateSize();
        this.deleted = false;
    }


    /**
     * The function calculates the total size of the Title, Description, and
     * Keywords strings.
     * 
     * @return The sum of the lengths of the Title, Description, and Keywords.
     */
    public int calculateSize() {
        return title.length() + description.length() + keywords.length();
    }
}
