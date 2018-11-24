import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return this.item;
        }
    }

    public Deque() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add null");
        Node newNode = new Node(item);

        newNode.next = head.next;
        head.next.prev = newNode;
        head.next = newNode;
        newNode.prev = head;

        this.size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add null");
        Node newNode = new Node(item);

        newNode.prev = tail.prev;
        tail.prev.next = newNode;
        tail.prev = newNode;
        newNode.next = tail;

        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException("queue is empty");
        Node toBeRemoved = head.next;
        head.next = toBeRemoved.next;
        toBeRemoved.next.prev = head;
        this.size--;
        return toBeRemoved.getItem();
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException("queue is empty");
        Node toBeRemoved = tail.prev;
        tail.prev = toBeRemoved.prev;
        toBeRemoved.prev.next = tail;
        this.size--;
        return toBeRemoved.getItem();

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head.next;

        public boolean hasNext() {
            return current.getItem() != null;
        }

        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException("no next node");

            Item item = current.getItem();
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // Deque<Integer> deque = new Deque<Integer>();
        // deque.addFirst(1);
        // deque.addLast(2);
        // deque.removeLast();
        // int count = 0;
        // for (int ele : deque) {
        //     count++;
        // }
        // StdOut.println(count);
    }
}
