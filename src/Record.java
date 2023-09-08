/**
 * The `Record` class represents a record with various properties such as ID,
 * title, date, length, cost, coordinates, description, keywords, size, and
 * deletion status.
 *
 * @author Aayush Bagrecha
 * @author Yash Shrikant
 * @version 1.0
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
     * Constructs a new `Record` object with the specified properties.
     *
     * @param id
     *            The unique identifier (ID) of the record.
     * @param title
     *            The title of the record.
     * @param date
     *            The date associated with the record.
     * @param length
     *            The length of the record.
     * @param x
     *            The X-coordinate of the record.
     * @param y
     *            The Y-coordinate of the record.
     * @param cost
     *            The cost associated with the record.
     * @param description
     *            The description of the record.
     * @param keywords
     *            The keywords associated with the record.
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
     * Calculates the total size of the Title, Description, and Keywords
     * strings.
     *
     * @return The sum of the lengths of the Title, Description, and Keywords.
     */
    public int calculateSize() {
        return title.length() + description.length() + keywords.length();
    }


    /**
     * Returns the unique identifier (ID) of the record.
     *
     * @return The ID of the record.
     */
    public int getId() {
        return id;
    }


    /**
     * Returns the title of the record.
     *
     * @return The title of the record.
     */
    public String getTitle() {
        return title;
    }


    /**
     * Returns the date associated with the record.
     *
     * @return The date of the record.
     */
    public String getDate() {
        return date;
    }


    /**
     * Returns the length of the record.
     *
     * @return The length of the record.
     */
    public int getLength() {
        return length;
    }


    /**
     * Returns the X-coordinate of the record.
     *
     * @return The X-coordinate of the record.
     */
    public short getX() {
        return x;
    }


    /**
     * Returns the Y-coordinate of the record.
     *
     * @return The Y-coordinate of the record.
     */
    public short getY() {
        return y;
    }


    /**
     * Returns the cost associated with the record.
     *
     * @return The cost of the record.
     */
    public int getCost() {
        return cost;
    }


    /**
     * Returns the description of the record.
     *
     * @return The description of the record.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Returns the keywords associated with the record.
     *
     * @return The keywords of the record.
     */
    public String getKeywords() {
        return keywords;
    }


    /**
     * Returns the deletion status of the record.
     *
     * @return `true` if the record is marked as deleted, `false` otherwise.
     */
    public boolean isDeleted() {
        return deleted;
    }


    /**
     * Sets the deletion status of the record.
     *
     * @param deleted
     *            `true` to mark the record as deleted, `false` otherwise.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
