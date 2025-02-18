////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.state;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionary;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.values.StringValue;

import java.io.BufferedReader;
import java.util.Set;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class FileTable implements IFileTable {

    // FILE TABLE STRUCTURE
    MyIDictionary<StringValue, BufferedReader> fileTable;

    // FILE TABLE CONSTRUCTORS
    public FileTable() { this.fileTable = new MyDictionary<>(); }

    // FILE TABLE METHODS
    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    @Override
    public void put(StringValue key, BufferedReader value) throws MyDictionaryException {
        this.fileTable.put(key, value);
    }

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public BufferedReader remove(StringValue key) throws MyDictionaryException {
        return this.fileTable.remove(key);
    }

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    @Override
    public BufferedReader update(StringValue key, BufferedReader newValue) throws MyDictionaryException {
        return this.fileTable.update(key, newValue);
    }

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    @Override
    public boolean containsKey(StringValue key) {
        return this.fileTable.containsKey(key);
    }

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public BufferedReader get(StringValue key) throws MyDictionaryException {
        return this.fileTable.get(key);
    }

    // Get KeySet
    // Returns the set of keys
    @Override
    public Set<StringValue> keySet() { return this.fileTable.keySet(); }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() {
        return this.fileTable.isEmpty();
    }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() {
        return this.fileTable.size();
    }

    // String Formatting
    @Override
    public String toString() {
        return this.fileTable.toString();
    }

    // Deep Copy
    // Returns a deep copy of the structure
    @Override
    public IFileTable deepCopy() {
        FileTable newFileTable = new FileTable();

        for (StringValue key : this.fileTable.keySet()) {
            try {
                BufferedReader value = this.fileTable.get(key);

                // BufferedReaders are immutable
                newFileTable.put((StringValue) key.deepCopy(), value);
            } catch (MyDictionaryException e) {
                throw new RuntimeException("Error during FileTable deep copy: " + e.getMessage());
            }
        }

        return newFileTable;
    }
}
