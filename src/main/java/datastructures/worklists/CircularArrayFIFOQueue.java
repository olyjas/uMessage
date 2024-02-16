package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    public E[] circularArray;
    private int front;
    private int back;

    private int size;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.circularArray = (E[]) new Comparable[capacity];
        this.front = 0;
        // indicates circular array is empty and incrementing back will make index 0 and the front E in the array
        this.back = -1;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if(isFull()) {
            throw new IllegalStateException();
        }
        back = (back + 1) % circularArray.length;
        circularArray[back] = work;
        size++;

    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return circularArray[front];
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (i >= capacity() - front) {
            return circularArray[((front + i) % capacity())];
        }
        return circularArray[front + i];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E removedE = circularArray[front];
        circularArray[front] = null;
        front = (front + 1) % circularArray.length;
        size--;
        return removedE;
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }

        if (i >= capacity() - front) {
            circularArray[((front + i) % capacity())] = value;
        }
        circularArray[front + i] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.front = 0;
        this.front = 0;
        this.back = 0;
        circularArray = (E[]) new Comparable[super.capacity()];
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        // check the sizes, if length of this is smaller, it will be less than
        int smallerLength = Math.min(this.size(), other.size());
        int compareValue = 0;
        for (int i = 0; i < smallerLength; i++) {
            compareValue = this.peek(i).compareTo(other.peek(i));
            if (compareValue != 0) {
                return compareValue;
            }
        }
        return this.size() - other.size();

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        // if the current circular array is equal to obj (the same circular array) automatically return true
        if (this == obj) {
            return true;
            // if the object we are comparing to is not an object of the type, automatically false
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
            //
        } else {
            // object IS same object type as this
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            // if the sizes are not equal, it won't be the same
            if (other.size() != this.size()) {
                return false;

            } else {
                return (this.compareTo(other) == 0);
            }
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int hash = 1;
        for (int i = 0; i < size; i++) {
            hash = hash * prime + this.peek(i).hashCode();
        }
        return hash;
    }
}
