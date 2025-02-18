////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.state;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionary;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class SymTable implements ISymTable {

    // SYMBOLIC TABLE STRUCTURE
    MyIDictionary<String, Value> symbolsTable;

    // SYMBOLIC TABLE CONSTRUCTORS
    public SymTable() { this.symbolsTable = new MyDictionary<>(); }

    // SYMBOLIC TABLE METHODS
    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    @Override
    public void put(String key, Value value) throws MyDictionaryException {
        this.symbolsTable.put(key, value);
    }

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public Value remove(String key) throws MyDictionaryException {
        return this.symbolsTable.remove(key);
    }

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    @Override
    public Value update(String key, Value newValue) throws MyDictionaryException {
        return this.symbolsTable.update(key, newValue);
    }

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    @Override
    public boolean containsKey(String key) {
        return this.symbolsTable.containsKey(key);
    }

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public Value get(String key) throws MyDictionaryException {
        return this.symbolsTable.get(key);
    }

    // Get Content
    // Returns the hash map of all values
    @Override
    public MyIDictionary<String, Value> getContent() {
        return this.symbolsTable;
    }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() {
        return this.symbolsTable.isEmpty();
    }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() {
        return this.symbolsTable.size();
    }

    // String Formatting
    @Override
    public String toString() {
        return this.symbolsTable.toString();
    }

    // Deep Copy
    // Returns a deep copy of the structure
    @Override
    public ISymTable deepCopy() {
        SymTable newSymTable = new SymTable();

        for (String key : this.symbolsTable.getContent().keySet()) {
            try {
                Value value = this.symbolsTable.get(key);
                newSymTable.put(key, value.deepCopy());
            } catch (MyDictionaryException e) {
                throw new RuntimeException("Error during SymTable deep copy: " + e.getMessage());
            }
        }

        return newSymTable;
    }
}
