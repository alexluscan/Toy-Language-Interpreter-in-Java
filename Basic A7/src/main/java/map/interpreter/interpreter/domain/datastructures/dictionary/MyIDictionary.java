////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.datastructures.dictionary;


import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface MyIDictionary<K, V> {

    // MY I DICTIONARY METHODS

    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    void put(K key, V value) throws MyDictionaryException;

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    V remove(K key) throws MyDictionaryException;

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    V update(K key, V newValue) throws MyDictionaryException;

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    boolean containsKey(K key);

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    V get(K key) throws MyDictionaryException;

    // Get Content
    // Returns the hash map of all values
    HashMap<K, V> getContent();

    // Get KeySet
    // Returns the set of keys
    Set<K> keySet();

    // Get Values
    // Returns the collection of values
    Collection<V> values();

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    boolean isEmpty();

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    int size();

    // Deep Copy
    // Returns a deep copy of the structure
    MyIDictionary<K, V> deepCopy();
}
