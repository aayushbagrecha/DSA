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


    // Serialization method to convert the SeminarRecord to a byte array
    public byte[] serialize() {
        try {
            ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Deserialization method to create a SeminarRecord from a byte array
    public static SeminarRecord deserialize(byte[] data) {
        try {
            ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
            return (SeminarRecord)objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Date: ").append(dateTime).append(", ");
        sb.append("Length: ").append(length).append(", ");
        sb.append("X: ").append(x).append(", Y: ").append(y).append(", ");
        sb.append("Cost: ").append(cost).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Keywords: ");
        for (String keyword : keywords) {
            sb.append(keyword).append(", ");
        }
        // Remove the trailing ", " from the last keyword
        if (keywords.length > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }
}
