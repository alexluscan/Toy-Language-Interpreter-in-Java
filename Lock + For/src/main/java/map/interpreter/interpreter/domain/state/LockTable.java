package map.interpreter.interpreter.domain.state;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionary;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.values.Value;

import java.util.HashMap;
import java.util.Map;

public class LockTable implements ILockTable {

    // LOCK TABLE STRUCTURE
    // LockTable is based on a <integer, integer> map
    MyIDictionary<Integer, Integer> lockTable;
    // LockTable keeps track of the next free location in memory
    int nextFree;

    // LOCK TABLE CONSTRUCTOR
    public LockTable() {
        this.lockTable = new MyDictionary<>();
        nextFree = 1;
    }


    // LOCK TABLE METHODS
    // Set Content
    // Sets the content of the heap to the new one
    @Override
    public synchronized void setContent(Map<Integer, Integer> newContent) {
        this.lockTable.clear();
        this.lockTable.putAll(newContent);
    }


    // Put
    // Adds a new value to the dictionary in the next free "address"
    @Override
    public synchronized Integer put(Integer value) throws MyDictionaryException {
        this.lockTable.put(this.nextFree, value);
        this.nextFree += 1;

        return this.nextFree - 1;
    }

    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    @Override
    public synchronized void put(Integer key, Integer value) throws MyDictionaryException {
        this.lockTable.put(key, value);
    }

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public synchronized Integer remove(Integer key) throws MyDictionaryException {
        return this.lockTable.remove(key);
    }

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    @Override
    public synchronized Integer update(Integer key, Integer newValue) throws MyDictionaryException {
        return this.lockTable.update(key, newValue);
    }

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    @Override
    public synchronized boolean containsKey(Integer key) {
        return this.lockTable.containsKey(key);
    }

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public synchronized Integer get(Integer key) throws MyDictionaryException {
        return this.lockTable.get(key);
    }

    // Get Content
    // Returns the hash map of all values
    @Override
    public synchronized HashMap<Integer, Integer> getContent() {
        return this.lockTable.getContent();
    }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public synchronized boolean isEmpty() {
        return this.lockTable.isEmpty();
    }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public synchronized int size() {
        return this.lockTable.size();
    }

    // String Formatting
    @Override
    public synchronized String toString() {
        return this.lockTable.toString();
    }
}
