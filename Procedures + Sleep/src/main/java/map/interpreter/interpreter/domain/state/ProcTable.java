package map.interpreter.interpreter.domain.state;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionary;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.statements.IStmt;
import map.interpreter.interpreter.domain.values.Value;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class ProcTable implements IProcTable {

    // PROC TABLE STRUCTURE
    MyIDictionary<String, Pair<MyIList<String>, IStmt>> procTable;

    // HEAP CONSTRUCTOR
    public ProcTable() {
        this.procTable = new MyDictionary<>();
    }


    // HEAP METHODS
    // Set Content
    // Sets the content of the heap to the new one
    @Override
    public void setContent(Map<String, Pair<MyIList<String>, IStmt>> newContent) {
        this.procTable.clear();
        this.procTable.putAll(newContent);
    }

    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    @Override
    public void put(String key, Pair<MyIList<String>, IStmt> value) throws MyDictionaryException {
        this.procTable.put(key, value);
    }

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public Pair<MyIList<String>, IStmt> remove(String key) throws MyDictionaryException {
        return this.procTable.remove(key);
    }

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    @Override
    public Pair<MyIList<String>, IStmt> update(String key, Pair<MyIList<String>, IStmt> newValue) throws MyDictionaryException {
        return this.procTable.update(key, newValue);
    }

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    @Override
    public boolean containsKey(String key) {
        return this.procTable.containsKey(key);
    }

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public Pair<MyIList<String>, IStmt> get(String key) throws MyDictionaryException {
        return this.procTable.get(key);
    }

    // Get Content
    // Returns the hash map of all values
    @Override
    public HashMap<String, Pair<MyIList<String>, IStmt>> getContent() {
        return this.procTable.getContent();
    }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() {
        return this.procTable.isEmpty();
    }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() {
        return this.procTable.size();
    }

    // String Formatting
    @Override
    public String toString() { return this.procTable.toString(); }
}
