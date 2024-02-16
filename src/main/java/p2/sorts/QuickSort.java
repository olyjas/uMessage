
package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        sort(array, comparator, 0, array.length);
    }

    private static <E> void sort(E[] array, Comparator<E> comparator, int end, int begin) {
        if (begin - end >= 2) {
            E pivot = array[begin - 1];
            int divide = end - 1;
            for (int i = end; i < begin; i++) {
                if (comparator.compare(array[i], pivot) <= 0) {
                    divide++;
                    E temp = array[divide];
                    array[divide] = array[i];
                    array[i] = temp;
                }
            }
            sort(array, comparator, end, divide);
            sort(array, comparator, divide + 1, begin);
        }
    }
}