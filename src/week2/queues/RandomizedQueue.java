package week2.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;

    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = queue[i];
        queue = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {

        if (item==null) {
            throw new IllegalArgumentException();
        }

        if (size == queue.length) {
            resize(2* queue.length);
        }

        queue[size] = item;
        size++;

    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int n = StdRandom.uniform(size);

        Item item = queue[n]; //store the value of item to be removed
        queue[n] = queue[size-1]; //move last item in array to replace item to be removed
        queue[size-1] = null;//set last item to null
        size--;

        if (size > 0 && size==queue.length/4) {
            resize(queue.length/2);
        }

        return item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int n = StdRandom.uniform(size);

        //System.out.println(n);

        return queue[n];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        int n = 0;
        private RandomIterator() {
            for (int i=size-1; i>0; i--) {
                int n = StdRandom.uniform(size);

                Item item = queue[n];
                queue[n] = queue[i];
                queue[i] = item;
            }
        }

        public boolean hasNext() {
            return n < size;
        }

        public Item next() {
            if (hasNext()) {
                return queue[n++];
            }
            throw new UnsupportedOperationException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        System.out.println("Starting");

        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        System.out.println("\nSample1 : " + queue.sample());
        System.out.println("Sample2 : " + queue.sample());
        queue.enqueue("f");
        System.out.println("Dequeue1: " + queue.dequeue());
        queue.enqueue("g");
        queue.enqueue("h");
        System.out.println("Dequeue2: " + queue.dequeue());
        System.out.println("Sample3: " + queue.sample());
        System.out.println("Size: " + queue.size());
        System.out.println(queue.isEmpty());

        System.out.println("\nMethod tests done");

        for (String s: queue) {
            System.out.println(s);
        }

    }

}