package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

// don't call find(K key, V value) method with a non-null second argument
    // don't write a separate find(key) method
public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // TODO: Implement me!

    // inner class of AVLNode
    private class AVLNode extends BSTNode {
        // added field for height
        // BSTNode array is length 2 with index 0 as left node and index 1 as right node
        int height;
        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }

    // x = root.key()
    //t.element = key
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        AVLNode curr = castAVLNode(this.root); // root node to start insertion with
        int child = -1;
        if (find(key)!=null) {
            // this key exists already, so we just change the value
            AVLNode prev = null;
            // we update the previous
            // from find(K key, V value)
            while (curr != null) {
                int direction = Integer.signum(key.compareTo(curr.key));

                if (direction == 0) {
                    V val = curr.value; // preserve original value to return
                    curr.value = value; //update curr node value to the one passed in
                    return val;
                } else {
                        child = Integer.signum(direction + 1);
                        prev = curr;
                        curr = castAVLNode(curr.children[child]);
                }
            }
        }
        this.root = insert(key, value, curr);
        this.size++;
        return null;
    }
    // x = key
    //t.element = root.key
    private AVLNode insert(K key, V value, AVLNode node) {
        if (node == null) {
            // insert location for leaf to be inserted (because node is null)
            return new AVLNode (key, value);
        }
        int comparedResult = key.compareTo(node.key);

        // insert into left child
        if (comparedResult < 0) {
            node.children[0] = insert(key, value, (AVLNode) node.children[0]);
        }
        // insert into right child
        else if (comparedResult > 0) {
            node.children[1] = insert(key, value, (AVLNode) node.children[1]);
        }
        return checkBalance(node);
    }

    private int height(AVLNode node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }
    private AVLNode checkBalance(AVLNode node) {
        if (node == null) {
            return node;
        }
        if (height(castAVLNode(node.children[0])) - height(castAVLNode(node.children[1])) > 1) {
            if (height(castAVLNode(node.children[0].children[0])) >= height(castAVLNode(node.children[0].children[1]))) {
                node = rotateWithLeftChild(node);
            } else {
                node = doubleRotateWithLeftChild(node);
            }
        }
        else if (height(castAVLNode(node.children[1])) - height(castAVLNode(node.children[0])) > 1) {
            if (height(castAVLNode(node.children[1].children[1])) >= height(castAVLNode(node.children[1].children[0]))) {
                node = rotateWithRightChild(node);
            } else {
                node = doubleRotateWithRightChild(node);
            }
        }
        node.height = Math.max(height(castAVLNode(node.children[0])), height(castAVLNode(node.children[0])) + 1);
        return node;
    }

    private AVLNode rotateWithLeftChild(AVLNode AVL2) {
        AVLNode AVL1 = castAVLNode(AVL2.children[0]);
        AVL2.children[0] = castAVLNode(AVL1.children[1]);
        AVL1.children[1] = AVL2;
        AVL2.height = Math.max(height(castAVLNode(AVL2.children[0])), height(castAVLNode(AVL2.children[1])) + 1);
        AVL1.height = Math.max(height(castAVLNode(AVL1.children[0])), AVL2.height) + 1;
        return AVL1;
    }

    private AVLNode rotateWithRightChild(AVLNode AVL2) {
        AVLNode AVL1 = castAVLNode(AVL2.children[1]);
        AVL2.children[1] = castAVLNode(AVL1.children[0]);
        AVL1.children[0] = AVL2;
        AVL2.height = Math.max(height(castAVLNode(AVL2.children[1])), height(castAVLNode(AVL2.children[0])) + 1);
        AVL1.height = Math.max(height(castAVLNode(AVL1.children[1])), AVL2.height) + 1;
        return AVL1;
    }

    private AVLNode doubleRotateWithLeftChild(AVLNode AVL3) {
        AVL3.children[0] = rotateWithRightChild(castAVLNode(AVL3.children[0]));
        return rotateWithLeftChild(AVL3);
    }

    private AVLNode doubleRotateWithRightChild(AVLNode AVL3) {
        AVL3.children[1] = rotateWithLeftChild(castAVLNode(AVL3.children[1]));
        return rotateWithRightChild(AVL3);
    }

    private AVLNode castAVLNode (BSTNode bstNode) {
            return (AVLNode) bstNode;

    }
}




