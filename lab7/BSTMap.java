import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;


public class BSTMap<K extends Comparable<K>, V>implements Map61B<K, V> {
    private Node<K, V> root;
    private int size;

    @Override
    public Iterator iterator() {
        return null;
    }

    private class Node<K, V>{
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    @Override
    /** Removes all of the mappings from this map. */
    public void clear(){
        root = null;
        size = 0;
    }

    @Override
    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (key == null){
            throw new IllegalArgumentException("argument to containsKey() is null");
        }
        return get(key) != null;
    }

    @Override
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node<K, V> node, K key){
        if (key == null){
            throw  new IllegalArgumentException("calls get() with a null key");
        }
        if (node == null){
            return null;
        }
        if (key.compareTo(node.key) > 0){
            return get(node.right, key);
        }
        if (key.compareTo(node.key) < 0){
            return get(node.left, key);
        }
        return node.value;
    }

    @Override
    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    @Override
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (key == null){
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (value == null){
            remove(key);
        }
        root = put(key, value, root);
    }

    private Node<K,V> put(K key, V value, Node<K,V> node) {
        if (node == null){
            size += 1;
            return new Node<>(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0){
            put(key, value, node.right);
        }
        if (cmp < 0){
            put(key, value, node.left);
        }
        if (cmp == 0){
            node.value = value;
        }
        return node;
    }

    @Override
    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        return keySet(root, new HashSet<>());
    }
    /** 递归，把该节点和子节点按从小到大加入keySet*/
    /*1.明白并相信递归函数的作用
    * 2.找到基本情况
    * 3。相信并利用递归*/
    private Set<K> keySet(Node<K, V> node, Set<K> keySet){
        if (node == null){
            return keySet;
        }
        keySet(node.left, keySet);
        keySet.add(node.key);
        keySet(node.right, keySet);
        return keySet;
    }
    @Override
    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V valueToRemove = get(key);
        if (valueToRemove == null) {
            return null;
        }
        root = remove(key, root);
        size -= 1;
        return valueToRemove;
    }

    @Override
    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V valueToRemove = get(key);
        if (valueToRemove != value) {
            throw new IllegalArgumentException("key and value don't match.");
        }
        root = remove(key, root);
        size -= 1;
        return valueToRemove;
    }

    /** Return the modified node after removing key from it. */
    private Node<K, V> remove(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = remove(key, node.left);
        } else if (cmp > 0) {
            node.right = remove(key, node.right);
        } else {
            /* node has 0 or 1 child. */
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            /* node with 2 children. */
            Node<K, V> successor = removeSuccessor(node);
            node.key = successor.key;
            node.value = successor.value;
        }
        return node;
    }

    /** Remove and return the successor of a two-child node. */
    private Node<K, V> removeSuccessor(Node<K, V> node) {
        Node<K, V> successorParent = node;
        Node<K, V> successor = node.right;

        if (successor.left == null) {
            successorParent.right = successor.right;
            return successor;
        }

        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }

        successorParent.left = successor.right;
        return successor;
    }
}