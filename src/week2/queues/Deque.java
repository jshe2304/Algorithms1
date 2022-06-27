package week2.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    //Memory = n(24 + Item) +

    private Node first = null;
    private Node last = null;

    private int size = 0;

    // construct an empty deque
    public Deque() {}

    private class Node {

        //Memory = 16n + 8n + Item*n
        Item item;

        Node previous = null;
        Node next = null;
    }


    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldfirst = first;

        first = new Node();

        first.item = item;

        if (oldfirst != null) {
            oldfirst.previous = first;
        }

        first.next = oldfirst;

        if (size == 0) {
            last = first;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        //next is null, previous is oldlast, item is item

        Node oldlast = last;

        last = new Node();

        last.item = item;

        last.previous = oldlast;

        if (oldlast != null) {
            oldlast.next = last;
        }

        if (size == 0) {
            first = last;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        first = first.next;

        size--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {

        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = last.item;

        //last.previous
        //last
        //last.next

        last = last.previous;

        if (last != null) {
            last.next = null;
        } else {
            first = last;
        }

        size--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("a");
        deque.addLast("b");
        deque.addFirst("c");
        deque.addLast("d");

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());

        for (String s: deque) {
            System.out.println(s);
        }
    }
}