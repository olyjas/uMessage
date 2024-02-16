package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> sortingHeap = new MinFourHeap<E>(comparator);
        for (E item : array) {
            sortingHeap.add(item);
        }
        int i = 0;
        while (sortingHeap.size() > 0) {
            array[i] = sortingHeap.next();
            i++;
        }
    }
}
