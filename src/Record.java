public class Record {
    int Id;
    String Title;
    String Date;
    int Length, Cost;
    short X, Y;
    String Description, Keywords;
    int Size;
    boolean deleted;

    public Record(
        int Id,
        String Title,
        String Date,
        int Length,
        short X,
        short Y,
        int Cost,
        String Description,
        String Keywords) {
        this.Id = Id;
        this.Title = Title;
        this.Date = Date;
        this.Length = Length;
        this.X = X;
        this.Y = Y;
        this.Cost = Cost;
        this.Description = Description;
        this.Keywords = Keywords;
        this.Size = calculateSize();
        this.deleted = false;
    }


    public int calculateSize() {
        // Calculate the size of the record including metadata
        // Modify this based on your specific metadata size
        return Title.length() + Description.length() + Keywords.length();
    }
}
