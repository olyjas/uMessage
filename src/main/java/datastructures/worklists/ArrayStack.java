package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    //fields
    private E[] arrayStack;
    private int size;
    private int index;
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.arrayStack = (E[]) new Object[10];
        this.size = 0;
        this.index = 0;
    }

    @Override
    public void add(E work) {
        if (index >= arrayStack.length) {
            int updatedCapacity = arrayStack.length * 2;
            E[] newArrayStack = (E[]) new Object[updatedCapacity];

            for (int i = 0; i < arrayStack.length; i++) {
                newArrayStack[i] = arrayStack[i];
            }
            arrayStack = newArrayStack;
        }
        // index field is set to 0 initially, so we add work element as that index in the array
        arrayStack[index] = work;
        // we increment the size of the index to get ready for future operations, although that index of the array
        // is not filled entirely yet
        index++;
        size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        // the index of the index variable is not filled with anything yet, so we must return the index-1 value
        return arrayStack[index - 1];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        index--;
        E poppedElement = arrayStack[index];
        arrayStack[index] = null;
        size--;
        return poppedElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        arrayStack = (E[]) new Object[10];
        size = 0;
        index = 0;
    }
}
