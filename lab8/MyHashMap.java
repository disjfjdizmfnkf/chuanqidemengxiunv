import edu.princeton.cs.algs4.SET;

import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private K key;
    private V value;
    private Set<K> hashSet;
    private int size = 0;
    private int 

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        hashSet = null;
    }

    /** Returns true if this map contains a mapping forth e specified key. */
    @Override
    public boolean containsKey(K key) {
        return !(get(key) == null);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {

    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return null;
    }

    //不实现
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
