////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.state;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.list.MyList;
import map.interpreter.interpreter.domain.datastructures.list.MyListException;
import map.interpreter.interpreter.domain.values.Value;

import java.util.List;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class OutList implements IOutList {

    // OUTPUT LIST STRUCTURE
    MyIList<Value> outputList;

    // OUTPUT LIST CONSTRUCTORS
    public OutList(){ this.outputList = new MyList<>();}

    // OUTPUT LIST METHODS
    // Add
    // Adds a new element to the end of the list
    @Override
    public void add(Value object) {
        this.outputList.add(object);
    }

    // Add
    // Adds a new element on the specified index
    // Throws an exception if specified index is invalid
    @Override
    public void add(int index, Value object) throws MyListException {
        this.outputList.add(index, object);
    }

    // Remove
    // Removes the element found at the specified index
    // Throws an exception if specified index is invalid
    @Override
    public Value remove(int index) throws MyListException {
        return this.outputList.remove(index);
    }

    // Set
    // Updates the element found at the specified position
    // Throws an exception if specified index is invalid
    @Override
    public Value set(int index, Value newObject) throws MyListException {
        return this.outputList.set(index, newObject);
    }

    // Get
    // Gets the element found at the specified position
    // Throws an exception if specified index is invalid
    @Override
    public Value get(int index) throws MyListException {
        return this.outputList.get(index);
    }

    // Get Content
    // Gets the entire content of the output list
    @Override
    public List<Value> getContent() {
        return this.outputList.getContent();
    }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() {
        return this.outputList.isEmpty();
    }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() {
        return this.outputList.size();
    }

    // String Formatting
    @Override
    public String toString() {
        return this.outputList.toString();
    }

    // Deep Copy
    // Returns a deep copy of the structure
    @Override
    public IOutList deepCopy() {
        OutList newOutList = new OutList();

        for (int i = 0; i < this.outputList.size(); i++) {
            try {
                Value value = this.outputList.get(i);
                newOutList.add(value.deepCopy());
            } catch (MyListException e) {
                throw new RuntimeException("Error during OutList deep copy: " + e.getMessage());
            }
        }

        return newOutList;
    }
}
