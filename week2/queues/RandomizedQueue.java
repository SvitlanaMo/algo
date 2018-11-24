import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private final ResizingArrayQueue<Item> rq;

    public RandomizedQueue() {
        this.rq = new ResizingArrayQueue<Item>();
    }

    private class ResizingArrayQueue<Item> {
        private Item[] arr = (Item[]) new Object[2];
        private int size = 0;

        public void push(Item item) {
            if (size == arr.length) resize(2 * arr.length);
            arr[size++] = item;
        }

        public Item pop() {
            Item item = arr[--size];
            arr[size] = null;
            if (size > 0 && size == arr.length / 4) resize(arr.length / 2);
            return item;
        }

        public Item getAt(int idx) {
            return arr[idx];
        }

        public void swapToLast(int idx) {
            Item tmp = arr[idx];
            int lastIdx = size - 1;
            arr[idx] = arr[lastIdx];
            arr[lastIdx] = tmp;
        }

        public int size() {
            return size;
        }

        private void resize(int capacity) {
            Item[] copy = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                copy[i] = arr[i];
            }
            arr = copy;
        }
    }

    public boolean isEmpty() {
        return this.rq.size() == 0;
    }

    public int size() {
        return this.rq.size();
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("can not enqueue with null");
        this.rq.push(item);
    }

    // remove and return a random item
    public Item dequeue() {
        int idx = this.getRandomIdx();
        this.rq.swapToLast(idx);
        return rq.pop();
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int idx = this.getRandomIdx();
        return rq.getAt(idx);
    }

    private int getRandomIdx() {
        int rqSize = this.rq.size();
        if (rqSize == 0) throw new NoSuchElementException("queue is empty");
        int idx = StdRandom.uniform(rqSize);
        return idx;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        int totalNumber = rq.size();
        Item[] results = (Item[]) new Object[totalNumber];

        // copy all to results
        for (int i = 0; i < totalNumber; i++) {
            results[i] = rq.getAt(i);
        }

        // shuffle
        for (int j = 0; j < totalNumber; j++) {
            int swapIdx = StdRandom.uniform(j, totalNumber);
            Item tmp = results[j];
            results[j] = results[swapIdx];
            results[swapIdx] = tmp;
        }


        return new ListIterator(results);
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] innerArr;
        private int cursor = 0;

        public ListIterator(Item[] arr) {
            this.innerArr = arr;
        }

        public boolean hasNext() {
            return this.cursor < this.innerArr.length;
        }

        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException("queue is empty");
            Item item = this.innerArr[cursor];
            cursor++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // UncommentedEmptyMethodBody
        // RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        // rq.enqueue(1);
        // rq.enqueue(2);
        // rq.enqueue(3);
        // rq.enqueue(4);
        // rq.enqueue(5);
        // rq.enqueue(6);
        //
        // int count = 0;
        // for (int ele : rq) {
        //     StdOut.println("1: =" + ele);
        //     count++;
        // }
        //
        // for (int ele : rq) {
        //     StdOut.println("2: =" + ele);
        //     count++;
        // }
        //
        // StdOut.println(count);
    }
}
