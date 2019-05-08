import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Your implementation of HashMap.
 * 
 * @author Cameorn Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 11;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public HashMap() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of the initialCapacity parameter.
     *
     * You may assume the initialCapacity parameter will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.

     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. As a
     * warning, be careful about using integer division in the LF calculation!
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException(
                "Cannot insert value with null key");
        }
        if (value == null) {
            throw new IllegalArgumentException(
                "Cannot insert null value");
        }
        if ((((float) (++size))/table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int hashIdx = Math.abs(key.hashCode() % table.length);
        if (table[hashIdx] == null) {
            table[hashIdx] = new MapEntry<K, V>(key, value);
            return null;
        } else {
            MapEntry<K, V> curr = table[hashIdx];
            MapEntry<K, V> temp = curr;
            while (curr != null) {
                if (curr.getKey().equals(key)) {
                    V tempVal = curr.getValue();
                    curr.setValue(value);
                    if (size > 1) {
                        size--;
                    }
                    return tempVal;
                }
                curr = curr.getNext();
            }
            table[hashIdx] = new MapEntry<K, V>(key, value, temp);
            return null;
        }
    }


    /** private put helper for resizing
     * return same as put
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    private V putNoResize(K key, V value) {
        int hashIdx = Math.abs(key.hashCode() % table.length);
        if (table[hashIdx] == null) {
            table[hashIdx] = new MapEntry<K, V>(key, value);
            return null;
        } else {
            MapEntry<K, V> curr = table[hashIdx];
            MapEntry<K, V> temp = curr;
            while (curr != null) {
                if (curr.getKey().equals(key)) {
                    V tempVal = curr.getValue();
                    curr.setValue(value);
                    if (size > 1) {
                        size--;
                    }
                    return tempVal;
                }
                curr = curr.getNext();
            }
            table[hashIdx] = new MapEntry<K, V>(key, value, temp);
            return null;
        }
    }

    /**
     * Resizes the backing table to the specified length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index, and
     * iterate over each chain from front to back. Add entries to the new table
     * in the order in which they are traversed.
     *
     * Remember, you cannot just simply copy the entries over to the new array.
     * You will have to rehash all of the entries and add them to the new index
     * of the new table. Feel free to create new MapEntry objects to use when
     * adding to the new table to avoid pointer dependency issues between the
     * new and old tables.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates. This matters especially for external chaining since it can
     * cause the performance of resizing to go from linear to quadratic time.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */
    public void resizeBackingTable(int length) {
        if (length < 1) {
            throw new IllegalArgumentException(
                "Length must be positive!");
        }
        if (length < size) {
            throw new IllegalArgumentException(
                "length must be less than map size");
        }
        MapEntry<K, V>[] temp = table;
        table = (MapEntry<K, V>[]) new MapEntry[length];
        for (MapEntry<K, V> curr : temp) {
            if (curr != null) {
                putNoResize(curr.getKey(), curr.getValue());
                while (curr.getNext() != null) {
                    curr = curr.getNext();
                    putNoResize(curr.getKey(), curr.getValue());
                }
            }
        }
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException(
                "Cannot remove null key");
        }
        int hashIdx = Math.abs(key.hashCode()) % table.length;
        MapEntry<K, V> curr = table[hashIdx];
        if (curr == null) {
            throw new java.util.NoSuchElementException(
                "Key was not found in this hashmap");
        } else {
            //check if key was at front of list or a non list
            if (curr.getKey().equals(key)) {
                V tempVal = curr.getValue();
                if (curr.getNext() == null) {
                    table[hashIdx] = null;
                    size--;
                    return tempVal;
                } else {
                    table[hashIdx] = curr.getNext();
                    size--;
                    return tempVal;
                }
            }
            while (curr.getNext() != null) {
                if (curr.getNext().getKey().equals(key)) {
                    V tempVal = curr.getNext().getValue();
                    curr.setNext(curr.getNext().getNext());
                    size--;
                    return tempVal;
                }
                curr = curr.getNext();
            }
            throw new java.util.NoSuchElementException(
                "Key was not found in hashmap");
        }
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException(
                "Key does not exist (null key)");
        }

        int hashIdx = Math.abs(key.hashCode() % table.length);
        if (table[hashIdx] == null) {
            throw new java.util.NoSuchElementException(
                "Key was not found in hashmap");
        } else {
            MapEntry<K, V> curr = table[hashIdx];
            if (curr.getKey().equals(key)) {
                return curr.getValue();
            }
            while (curr.getNext() != null) {
                curr = curr.getNext();
                if (curr.getKey().equals(key)) {
                    return curr.getValue();
                }
            }
            throw new java.util.NoSuchElementException(
                "Key was not found in hashmap");
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException(
                "Key does not exist (null key)");
        }

        int hashIdx = Math.abs(key.hashCode() % table.length);
        if (table[hashIdx] == null) {
            return false;
        } else {
            MapEntry<K, V> curr = table[hashIdx];
            if (curr.getKey().equals(key)) {
                return true;
            }
            while (curr.getNext() != null) {
                curr = curr.getNext();
                if (curr.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map. The Set view is
     * used instead of a List view because keys are unique in a HashMap, which
     * is a property that elements of Sets also share.
     * 
     * Use java.util.HashSet.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> ret = new HashSet<K>(size);
        for (MapEntry<K, V> x : table) {
            if (x != null) {
                ret.add(x.getKey());
                while (x.getNext() != null) {
                    x = x.getNext();
                    ret.add(x.getKey());
                }
            }
        }
        return ret;
    }

    /**
     * Returns a List view of the values contained in this map.
     * 
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index, and
     * iterate over each chain from front to back. Add entries to the List in
     * the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> ret = new ArrayList<V>(size);
        for (MapEntry<K, V> x : table) {
            if (x != null) {
                ret.add(x.getValue());
                while (x.getNext() != null) {
                    x = x.getNext();
                    ret.add(x.getValue());
                }
            }
        }
        return ret;
    }

    /**
     * Clears the table and resets it to a new table of length INITIAL_CAPACITY.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the size of the HashMap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
    
    /**
     * Returns the backing table of the HashMap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing table of the HashMap
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}