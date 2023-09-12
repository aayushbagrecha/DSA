public class Handle {
    private int startPosition;
    private int length;

    public Handle(int startPosition, int length) {
        this.startPosition = startPosition;
        this.length = length;
    }


    public int getStartPosition() {
        return startPosition;
    }


    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }


    public int getLength() {
        return length;
    }


    public void setLength(int length) {
        this.length = length;
    }
}
