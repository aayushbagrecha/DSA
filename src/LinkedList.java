public class LinkedList {
    private Node head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public void add(int offset) {
        Node newNode = new Node(offset);

        if (head == null) {
            head = newNode;
        }
        else {
            newNode.next = head;
            head = newNode;
        }

        size++;
    }


    public boolean contains(int offset) {
        Node current = head;
        while (current != null) {
            if (current.offset == offset) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    public int removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }

        Node removedNode = head;
        head = head.next;
        size--;

        return removedNode.offset;
    }


    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.offset + " ");
            current = current.next;
        }
        System.out.println();
    }

    private class Node {
        int offset;
        Node next;

        Node(int offset) {
            this.offset = offset;
            this.next = null;
        }
    }
}
