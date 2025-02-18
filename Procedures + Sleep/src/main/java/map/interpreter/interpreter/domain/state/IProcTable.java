package map.interpreter.interpreter.domain.state;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.statements.IStmt;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public interface IProcTable {

    // Set Content
    // Sets the content of the heap to the new one
    void setContent(Map<String, Pair<MyIList<String>, IStmt>> newContent);

    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    void put(String key, Pair<MyIList<String>, IStmt> value) throws MyDictionaryException;

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    Pair<MyIList<String>, IStmt> remove(String key) throws MyDictionaryException;

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    Pair<MyIList<String>, IStmt> update(String key, Pair<MyIList<String>, IStmt> newValue) throws MyDictionaryException;

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    boolean containsKey(String key);

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    Pair<MyIList<String>, IStmt> get(String key) throws MyDictionaryException;

    // Get Content
    // Returns the hash map of all values
    HashMap<String, Pair<MyIList<String>, IStmt>> getContent();

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    boolean isEmpty();

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    int size();
}
