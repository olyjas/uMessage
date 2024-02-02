package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;

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
        this.comparator = comparator;
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
            this.data = updatedArray;
        }
        this.data[this.size] = work;

        if (this.size > 0) {

        }
    }

    @Override
    public E peek() {
        throw new NotYetImplementedException();
    }

    @Override
    public E next() {
        throw new NotYetImplementedException();
    }

    @Override
    public int size() {
        throw new NotYetImplementedException();
    }

    @Override
    public void clear() {
        throw new NotYetImplementedException();
    }
}