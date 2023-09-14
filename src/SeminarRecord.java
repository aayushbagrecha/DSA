import java.io.*;

public class SeminarRecord implements Serializable {
    private int id;
    private String title;
    private String dateTime;
    private int length;
    private short x;
    private short y;
    private String description;
    private String[] keywords;
    private int cost;

    public SeminarRecord(
        int id,
        String title,
        String dateTime,
        int length,
        short x,
        short y,
        int cost,
        String description,
        String[] keywords) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.length = length;
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.description = description;
        this.keywords = keywords;
    }

    // Getter methods for all attributes


    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getDateTime() {
        return dateTime;
    }


    public int getLength() {
        return length;
    }


    public short getX() {
        return x;
    }


    public short getY() {
        return y;
    }


    public String getDescription() {
        return description;
    }


    public String[] getKeywords() {
        return keywords;
    }


    public int getCost() {
        return cost;
    }


    public static SeminarRecord deserialize(byte[] inputbytes)
        throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(inputbytes);
        try (ObjectInputStream inputStream = new ObjectInputStream(bis)) {
            int id = inputStream.readInt();
            String title = inputStream.readUTF();
            String date = inputStream.readUTF();
            int length = inputStream.readInt();
            short x = inputStream.readShort();
            short y = inputStream.readShort();
            int cost = inputStream.readInt();

            int numKeywords = inputStream.readInt();
            String[] keywords = new String[numKeywords];
            for (int i = 0; i < numKeywords; i++) {
                keywords[i] = inputStream.readUTF();
            }

            String desc = inputStream.readUTF();

            return new SeminarRecord(id, title, date, length, x, y, cost, desc,
                keywords);
        }
    }


    // ----------------------------------------------------------
    /**
     * Return the cannonical serialized form (as a byte array) for this seminar
     * object
     *
     * @return the byte array that is the serialization of this
     * @throws Exception
     *             from serialization
     */
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(out)) {
            outputStream.writeInt(id);
            outputStream.writeUTF(title);
            outputStream.writeUTF(dateTime);
            outputStream.writeInt(length);
            outputStream.writeShort(x);
            outputStream.writeShort(y);
            outputStream.writeInt(cost);

            // Write the number of keywords and then each keyword
            outputStream.writeInt(keywords.length);
            for (String keyword : keywords) {
                outputStream.writeUTF(keyword);
            }

            outputStream.writeUTF(description);
        }
        return out.toByteArray();
    }


    /**
     * @return a string representation of the object.
     */
    public String toString() {
        int i;
        String mykeys = "";
        for (i = 0; i < keywords.length; i++) {
            mykeys += keywords[i];
            if (i != keywords.length - 1)
                mykeys += ", ";
        }
        return "ID: " + id + ", Title: " + title + "\nDate: " + dateTime
            + ", Length: " + length + ", X: " + x + ", Y: " + y + ", Cost: "
            + cost + "\nDescription: " + description + "\nKeywords: " + mykeys;
    }
}
