package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private Node front;
    private Node back;
    private int size;
    private class Node {
        //fields
        public E data;
        public Node next;
        // Constructs a node with a data value
        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
    public ListFIFOQueue() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if (front == null) {
            front = new Node(work);
            back = front;
        } else {
            Node last = back;
            last.next = new Node(work);
            back = last.next;
        }
        this.size++;
    }
    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        else {
            return front.data;
        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        else {
            Node removed = front;
            front = front.next;
            this.size--;
            return removed.data;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.front = null;
        this.back = null;
        size = 0;
    }
}
