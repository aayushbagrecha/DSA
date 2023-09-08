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

    private int id;
    private String title;
    private String date;
    private int length;
    private int cost;
    private short x;
    private short y;
    private String description;
    private String keywords;
    private int size;
    private boolean deleted;

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


    /**
     * The function returns the value of the id variable.
     * 
     * @return The method is returning the value of the variable "id".
     */
    public int getId() {
        return id;
    }


    /**
     * The getTitle() function returns the title of an object.
     * 
     * @return The method is returning the value of the variable "title".
     */
    public String getTitle() {
        return title;
    }


    /**
     * The function returns the date as a string.
     * 
     * @return The method is returning a String value.
     */
    public String getDate() {
        return date;
    }


    /**
     * The function returns the length of something.
     * 
     * @return The method is returning the value of the variable "length".
     */
    public int getLength() {
        return length;
    }


    /**
     * The function getX() returns the value of the variable x, which is of type
     * short.
     * 
     * @return The method is returning the value of the variable "x" as a short
     *         data type.
     */
    public short getX() {
        return x;
    }


    /**
     * The function returns the value of the variable "y" as a short data type.
     * 
     * @return The method is returning the value of the variable "y", which is
     *         of type short.
     */
    public short getY() {
        return y;
    }


    /**
     * The function returns the cost value.
     * 
     * @return The method is returning the value of the variable "cost".
     */
    public int getCost() {
        return cost;
    }


    /**
     * The getDescription() function returns the description of an object.
     * 
     * @return The method is returning a String value.
     */
    public String getDescription() {
        return description;
    }


    /**
     * The function "getKeywords" returns a string containing keywords.
     * 
     * @return The method is returning a String value.
     */
    public String getKeywords() {
        return keywords;
    }


    /**
     * The function returns the deleted status.
     * 
     * @return The method is returning the value of the variable "deleted".
     */
    public boolean getDeletedStatus() {
        return deleted;
    }


    /**
     * The function sets the deleted status of an object.
     * 
     * @param deleted
     *            The "deleted" parameter is a boolean value that indicates
     *            whether an object or
     *            entity has been marked as deleted or not.
     */
    public void setDeletedStatus(boolean deleted) {
        this.deleted = deleted;
    }
}
