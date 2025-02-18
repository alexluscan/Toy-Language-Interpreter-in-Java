////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.datastructures.dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    // MY DICTIONARY STRUCTURE
    // Dictionary - based on hash map
    HashMap<K, V> dictionary;


    // MY DICTIONARY CONSTRUCTORS
    public MyDictionary() {
        dictionary = new HashMap<>();
    }


    // MY DICTIONARY METHODS
    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    @Override
    public void put(K key, V value) throws MyDictionaryException {
        // Check if the key is present inside the dictionary
        if(this.containsKey(key))
            throw new MyDictionaryException("Key was already found inside the Dictionary");

        // Adds the new (key, value) pair to the dictionary
        dictionary.put(key, value);
    }

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public V remove(K key) throws MyDictionaryException {

        // Check if the key is present inside the dictionary
        if(!this.containsKey(key))
            throw new MyDictionaryException("Key was not found inside the Dictionary");

        // Removes the (key, value) pair associated with the given key from the dictionary
        // Returns the previous value
        return dictionary.remove(key);
    }

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    public V update(K key, V newValue) throws MyDictionaryException {

        // Check if the key is present inside the dictionary
        if(!this.containsKey(key))
            throw new MyDictionaryException("Key was not found inside the Dictionary");

        // Updates a (key, value) pair in the dictionary
        return dictionary.replace(key, newValue);
    }

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    @Override
    public boolean containsKey(K key) {
        return dictionary.containsKey(key);
    }

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public V get(K key) throws MyDictionaryException {

        // Check if the key is present inside the dictionary
        if(!this.containsKey(key))
            throw new MyDictionaryException("Key was not found inside the Dictionary");

        // Returns the value associated to the given key
        return dictionary.get(key);
    }

    // Get Content
    // Returns the hash map of all values
    @Override
    public HashMap<K, V> getContent() {
        return this.dictionary;
    }

    // Get KeySet
    // Returns the set of keys
    @Override
    public Set<K> keySet() {
        return this.dictionary.keySet();
    }

    // Get Values
    // Returns the collection of values
    @Override
    public Collection<V> values() { return this.dictionary.values(); }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() {
        return dictionary.size();
    }

    // String Formatting
    @Override
    public String toString() {
        // Creating the String format of the Stack
        // Adding Start
        StringBuilder state = new StringBuilder("{");

        // Adding all elements inside the dictionary
        for (K key : dictionary.keySet()) {
            V value = dictionary.get(key);
            state.append(key).append("->").append(value).append(", ");
        }

        // Removing the last comma and space if dictionary is not empty
        if (!dictionary.isEmpty()) {
            state.setLength(state.length() - 2); // Remove the last ", "
        }

        // Adding End
        state.append("}");

        // Returning the State
        return state.toString();
    }

    // Deep Copy
    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyIDictionary<K, V> newDictionary = new MyDictionary<>();

        for (K key : this.dictionary.keySet()) {
            try {
                V value = this.dictionary.get(key);
                newDictionary.put(key, value);
            } catch (MyDictionaryException e) {
                throw new RuntimeException("Error during dictionary deep copy: " + e.getMessage());
            }
        }

        return newDictionary;
    }
}
