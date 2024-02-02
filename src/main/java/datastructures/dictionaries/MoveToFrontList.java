package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListItem front;

    private class ListItem {
        private Item<K, V> data;
        private ListItem next;

        public ListItem() {
            this(null, null);
        }

        public ListItem(Item<K, V> item) {
            this(item, null);
        }

        public ListItem(Item<K, V> item, ListItem next) {
            this.data = item;
            this.next = next;
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V previous = this.find(key);
        if (previous != null) {
            this.front.data.value = value;
        } else {
            this.front = new ListItem(new Item<>(key, value), this.front);
            this.size++;
        }
        return previous;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        ListItem curr = this.front;
        V value = null;
        if (curr != null && curr.data != null) {
            if (curr.data.key.equals(key)) {
                return curr.data.value;
            }
            while (curr.next != null && curr.next.data != null &&
                    !curr.next.data.key.equals(key)) {
                curr = curr.next;
            }
            if (curr.next != null && curr.next.data != null) {
                value = curr.next.data.value;
                ListItem nodeToMoveFront = curr.next;
                curr.next = nodeToMoveFront.next;
                nodeToMoveFront.next = this.front;
                this.front = nodeToMoveFront;
            }
            return value;
        } else {
            return null;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ListIterator();
    }

    private class ListIterator extends SimpleIterator<Item<K, V>> {
        private ListItem curr;

        public ListIterator() {
            if (front != null) {
                this.curr = front;
            }
        }

        public boolean hasNext() {
            return this.curr != null;
        }

        public Item<K, V> next() {
            if (this.curr == null) {
                throw new NoSuchElementException();
            }
            Item<K, V> value = new Item<K, V>(this.curr.data.key, this.curr.data.value);
            this.curr = this.curr.next;
            return value;
        }
    }
}