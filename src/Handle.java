public class Handle {
    private int startingPosition;
    private int recordLength;

    public Handle(int startingPosition, int recordLength) {
        this.startingPosition = startingPosition;
        this.recordLength = recordLength;
    }

    // Getters and setters for handle attributes


    public int getStartingPosition() {
        return startingPosition;
    }


    public void setStartingPosition(int startingPosition) {
        this.startingPosition = startingPosition;
    }


    public int getRecordLength() {
        return recordLength;
    }


    public void setRecordLength(int recordLength) {
        this.recordLength = recordLength;
    }
}
