package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private Comparator<E> comparator;

    public MinFourHeap(Comparator<E> c) {
        this.data = (E[]) new Object[10];
        this.size = 0;
        this.comparator = c;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        if (this.size == this.data.length) {
            E[] updatedArray = (E[]) new Object[this.size * 2];
            for (int i = 0; i < this.size; i++) {
                updatedArray[i] = this.data[i];
            }
            data = updatedArray;
        }
        data[size] = work;
        if (size > 0) {
            percolateUp(size);
        }
        size++;
    }

    @Override
    public E peek() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E value = data[0];
        data[0] = data[size - 1];
        size--;

        if (size > 0) {
            percolateDown(0);
        }
        return value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[])new Object[10];
        this.size = 0;
    }
    private int parent(int indexOfChild) {
        return (indexOfChild - 1) / 4;
    }

    private int child(int specificChild, int indexOfParent) {
        return 4 * indexOfParent + specificChild + 1;
    }

    private int priorityChildIndex(int firstChildIndex) {
        int priorityChild;
        if (firstChildIndex > size) {
            return -1;
        } else {
            priorityChild = firstChildIndex;
            for (int i = firstChildIndex + 1; i < firstChildIndex + 4; i++) {
                if (i < size && comparator.compare(data[i], data[priorityChild]) < 0) {
                    priorityChild = i;
                }
            }
        }
        return priorityChild;
    }

    private void percolateUp(int indexOfChild) {
        int indexOfParent;
        // child cannot be at root of heap
        // value of child compared to value of parent
        // if child value is smaller, loop continues
        while (indexOfChild != 0 &&
                comparator.compare(data[indexOfChild], data[indexOfParent = parent(indexOfChild)]) < 0) {
            E childValStorage = data[indexOfChild];
            // moves child up the heap
            data[indexOfChild] = data[indexOfParent];
            data[indexOfParent] = childValStorage;
            indexOfChild = indexOfParent;
        }
    }

    private void percolateDown(int indexOfParent) {
        int priorityChildNode;
        // find index of child with smallest value among children of parent
        // parent value compares to smallest child value, parent value must not be larger than
        // child value so we loop until it doesn't
        while ((priorityChildNode = priorityChildIndex(child(0, indexOfParent))) != -1 &&
                comparator.compare(data[indexOfParent], data[priorityChildNode]) > 0) {

            E childValStorage = data[priorityChildNode];
            data[priorityChildNode] = data[indexOfParent];
            data[indexOfParent] = childValStorage;

            indexOfParent = priorityChildNode;
        }
    }
}