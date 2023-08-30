import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int default_initialSize = 16;
    private static final double default_loadFactor = 0.75;
    private int initialSize;
    private double loadFactor;
    private int threshold;           //阈值
    private int size = 0;
    private BucketEntity<K, V>[] hashTable;

    private static class BucketEntity<K, V>{
        private K key;
        private V value;
        private BucketEntity<K, V> next;

        private BucketEntity(K k, V v, BucketEntity<K, V> n) {
            key = k;
            value = v;
            next = n;
        }

        private BucketEntity(K k, V v) {
            this(k, v, null);
        }

        private BucketEntity<K, V> find(K key){     //helper method for recursion
            return find(key, this);
        }

        private BucketEntity<K, V> find(K key, BucketEntity<K, V> be){
            if (be == null){
                return null;
            }
            if (be.key.equals(key)){
                return be;
            }
            return find(key, be.next);
        }

        private boolean put(K key, V value) {
            return put(key, value, this);
        }

        private boolean put(K key, V value, BucketEntity<K, V> be) {
            if (be.key.equals(key)) {
                be.value = value;
                return false;
            }
            if (be.next == null) {
                be.next = new BucketEntity<>(key, value);
                return true;
            }
            return put(key, value, be.next);
        }

        private void add(BucketEntity<K, V> beToAdd) {
            BucketEntity<K, V> be = this;
            while (be.next != null) {
                be = be.next;
            }
            be.next = beToAdd;
        }
    }

    public MyHashMap(int initialSize, double loadFactor){
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.threshold = (int) (initialSize * loadFactor);
        hashTable = new BucketEntity[initialSize];
    }

    public MyHashMap(){this(default_initialSize, default_loadFactor);}

    public MyHashMap(int initialSize) { this(initialSize, default_loadFactor);}

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        hashTable = new BucketEntity[hashTable.length];
        size = 0;
    }

    /** Returns true if this map contains a mapping forth e specified key. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int keyHash = hash(key);
        if (hashTable[keyHash] == null) {
            return null;
        }
        BucketEntity<K, V> be = hashTable[keyHash].find(key);
        return be == null ? null : be.value;
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
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (value == null) {
            remove(key);
        }

        if (size + 1 > threshold) {
            resize();
        }

        int keyHash = hash(key);

        if (hashTable[keyHash] == null) {
            hashTable[keyHash] = new BucketEntity<>(key, value);
            size += 1;
        } else {
            boolean keyIsNew = hashTable[keyHash].put(key, value);
            if (keyIsNew) {
                size += 1;
            }
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet<K> hashKeys = new HashSet<>();
        for (BucketEntity<K, V> be : hashTable){
            hashKeys.add(be.key);
            be = be.next;
        }
        return hashKeys;
    }

    /**不实现*/
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
        return keySet().iterator();
    }

    private int hash(K key) {
        return hash(key, hashTable.length);
    }

    private int hash(K key, int capacity) {
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }

    private void resize() {
        BucketEntity<K, V>[] newHashTable = new BucketEntity[initialSize * 2];
        threshold = (int) (newHashTable.length * loadFactor);

        for (BucketEntity<K, V> bucket : hashTable) {
            if (bucket == null) {
                continue;
            }

            int newHash = hash(bucket.key, newHashTable.length);

            if (newHashTable[newHash] == null) {
                newHashTable[newHash] = bucket;
            } else {
                newHashTable[newHash].add(bucket);
            }
        }
    }
}
