package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private double loadFactor;
    private Dictionary<K,V>[] hashTable;
    private double max;
    private int prime;
    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};
    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.prime = 0;
        this.hashTable = (Dictionary<K, V>[]) new Dictionary[PRIME_SIZES[prime]];
        this.max = 0.5;
        loadFactor = (double) this.size / this.hashTable.length;
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        int index = Math.abs(key.hashCode() % this.hashTable.length);
        V val = null;
        if (loadFactor >= 2.0) {
            rehash();
            index = Math.abs(key.hashCode() % hashTable.length);
        }
        if (hashTable[index] == null) {
            hashTable[index] = newChain.get();
        }
        if (hashTable[index].find(key)== null) {
            size++;
        } else {
            val = hashTable[index].find(key);
        }
        hashTable[index].insert(key, value);
        loadFactor = ((double) (size + 1) / hashTable.length);

        return val;
    }

    private void checkHash() {
        this.loadFactor = (double) (this.size / this.hashTable.length);
        if (this.loadFactor >= max) {
            rehash();
        }
    }

    private void rehash() {
        int updatedSize = size;
        if (this.prime < PRIME_SIZES.length - 1) {
            updatedSize = PRIME_SIZES[this.prime];
            this.prime += 1;
        } else if (this.prime >= PRIME_SIZES.length) {
            updatedSize *= 2;
        }
        Dictionary<K, V>[] temp = (Dictionary<K, V>[]) new Dictionary[updatedSize];
        for(int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                Dictionary<K,V> currBucket = temp[i];
                for(Item<K,V> currItem: currBucket) {
                    insert(currItem.key, currItem.value);
                }
            }
        }
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = Math.abs(key.hashCode() % this.hashTable.length);
        if(hashTable[i] == null) {
            hashTable[i] = newChain.get();
            return null;
        }
        return this.hashTable[i].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new HashIterator();
    }

    private class HashIterator extends SimpleIterator<Item<K,V>> {
        private int index;
        private Iterator<Item<K, V>> dictionaryItr;

        public HashIterator() {
            index = 0;
            dictionaryItr = null;
        }
        @Override
        public boolean hasNext() {
            while(index < hashTable.length){
                if(dictionaryItr == null && hashTable[index] != null){
                    dictionaryItr = hashTable[index].iterator();
                }

                if(dictionaryItr != null) {
                    if(dictionaryItr.hasNext()) {
                        return true;
                    } else {
                        dictionaryItr = null;
                    }
                }
                index++;
            }
            return false;
        }

        @Override
        public Item<K, V> next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return new Item<K, V>(dictionaryItr.next());
        }
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        String stringRepresentation = "";

        for(int i = 0; i < this.hashTable.length; i++){
            if(this.hashTable[i] != null){
                stringRepresentation += this.hashTable[i].toString() + " ,";
            }
        }
        return stringRepresentation;
    }
}
