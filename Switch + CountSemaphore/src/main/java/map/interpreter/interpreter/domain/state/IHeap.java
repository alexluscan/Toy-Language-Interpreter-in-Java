////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.state;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.values.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface IHeap {

    // HEAP METHODS

    // Set Content
    // Sets the content of the heap to the new one
    void setContent(Map<Integer, Value> newHeapContent);

    // Put
    // Adds a new value to the dictionary in the next free "address"
    Integer put(Value value) throws MyDictionaryException;

    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    void put(Integer key, Value value) throws MyDictionaryException;

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    Value remove(Integer key) throws MyDictionaryException;

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    Value update(Integer key, Value newValue) throws MyDictionaryException;

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    boolean containsKey(Integer key);

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    Value get(Integer key) throws MyDictionaryException;

    // Get Content
    // Returns the hash map of all values
    HashMap<Integer, Value> getContent();

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    boolean isEmpty();

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    int size();

    // Garbage Collector Related Methods
    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap);
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues);
    public List<Integer> getReachableAddresses(List<Integer> symTableAddr, Map<Integer, Value> heap);

    // Deep Copy
    // Returns a deep copy of the structure
    IHeap deepCopy() throws MyDictionaryException;
}
