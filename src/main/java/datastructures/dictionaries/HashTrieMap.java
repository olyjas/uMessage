package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import datastructures.worklists.ArrayStack;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            // pointers is the hashmap
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        Iterator<A> itr = key.iterator();

        // the current hashtrienode is this.root to show it starts at the root
        HashTrieNode curr = (HashTrieNode) this.root;
        V previousValue = null;
        // while there are A values in K, we start at the current node and traverse down the branches
        // creating a new pointer (map) if A doesn't exist with value null
        // if it does exist, we keep traversing down until we reach the last A and update the value
        while (itr.hasNext()) {
            // curr.pointers is the hashmap associated with it that contains the key (A) and value
            // if it does already contain the key, we don't have to create a new HashTrieNode representing its value
            // because it will already exist as null
            // going through until itr.hasNext() has ran through completely means the value will be updated after
            // it has ran through completely

            // currElement updates as the next A element in K
            A currElement = itr.next();
            // if the current node does not contain that element ^
            if (!curr.pointers.containsKey(currElement)) {
                // creation of a new node (map) where the A in key is represented by null
                curr.pointers.put(currElement, new HashTrieNode());
            }
            // updating current node to the next node in the trie
            // (go through the if statement again above after curr has been updated as the next node)
            // so checking if the node contains the element as the key will be applied to the NEXT NODE
            curr = curr.pointers.get(currElement);
            // Assigning the last A in key as a last node (map) that DOES have a value
        }
        previousValue = curr.value;
        curr.value = value;
        size++;

        return previousValue;
    }


    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        // implementing iterator to go through all the A's in K
        Iterator<A> itr = key.iterator();
        // evaluating from the root of the HashTrieNode we keep going through the node's pointers where it will point
        // to other nodes (that are maps) that have keys and values
        HashTrieNode curr = (HashTrieNode) this.root;
        // traversing through the entire HashTrieMap through all the HashTrieNodes
        // K key = String dot
        while (itr.hasNext()) {
            // 1st time in loop, there is a next char - currElement = d
            // 2nd time in loop, there is a next char - currElement = o
            // 3rd time in loop, there is a next char - currElement = t
            // 4th time in loop, there is not another char - exit
            A currElement = itr.next();
            // 1st time - checks if the current node has a value of another node (map containing a key value of d)
            // 2nd time = checks if the current node has a value of another node (map containing a key value of o)
            // 3rd time - checks if the current node has a value of another node (map containing a key value of t)
            if (!curr.pointers.containsKey(currElement)) {
                return null;
            }
            // 1st time- if the current node does have a value of another node (map containing a key value of d)
            // the current node will now become the node that the prior node is pointing to
            // we go through the while loop again now
            // 2nd time - current node updates to the node that the prior node is pointing to
            // 3rd time ^
            curr = curr.pointers.get(currElement);
        }
        // returns the value of the last A in K to represent the entirety of all the A's in K
        return curr.value;
    }

    @Override

    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> itr = key.iterator();
        HashTrieNode curr = (HashTrieNode) this.root;
        while (itr.hasNext()) {
            A currElement = itr.next();
            if (!curr.pointers.containsKey(currElement)) {
                return false;
            }
            curr = curr.pointers.get(currElement);
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (find(key) == null) {
            return;
        }

        Iterator<A> itr = key.iterator();
        //start at the root
        HashTrieNode curr = (HashTrieNode) this.root;

        ArrayStack<HashTrieNode> nodeStack = new ArrayStack<>();

        ArrayStack<A> elementStack = new ArrayStack<>();
        nodeStack.add(curr);
        // traverse down
        while (itr.hasNext()) {
            A currElement = itr.next();

            elementStack.add(currElement);

            curr = curr.pointers.get(currElement);
            nodeStack.add(curr);
        }
        curr.value = null;

        while (nodeStack.size() > 1) {
            HashTrieNode children = nodeStack.next();

            if (children.pointers.size() > 0 || children.value != null) {
                break;
            }
            HashTrieNode removed = nodeStack.peek();
            removed.pointers.remove(elementStack.next());
            size--;
        }

    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
        size = 0;
    }
}
