////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.datastructures.list;
import java.util.ArrayList;
import java.util.List;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class MyList<T> implements MyIList<T> {

    // MY LIST STRUCTURE
    // List - based on array list
    List<T> list;


    // MY LIST CONSTRUCTORS
    public MyList() {
        list = new ArrayList<>();
    }


    // MY LIST METHODS
    // Add
    // Adds a new element to the end of the list
    @Override
    public void add(T object) {
        list.add(object);
    }

    // Add
    // Adds a new element on the specified index
    // Throws an exception if specified index is invalid
    @Override
    public void add(int index, T object) throws MyListException {

        // Check the specified index
        if( index < 0 || index > this.size())
            throw new MyListException("Invalid index for Addition");

        // Add the new object to the specified index
        list.add(index, object);
    }

    // Remove
    // Removes the element found at the specified index
    // Throws an exception if specified index is invalid
    @Override
    public T remove(int index) throws MyListException {

        // Check the specified index
        if( index < 0 || index > this.size())
            throw new MyListException("Invalid index for Removal");

        // Remove the object from the specified index
        return list.remove(index);
    }

    // Set
    // Updates the element found at the specified position
    // Throws an exception if specified index is invalid
    @Override
    public T set(int index, T newObject) throws MyListException {

        // Check the specified index
        if( index < 0 || index > this.size())
            throw new MyListException("Invalid index for Update");

        // Update the object at the specified index
        return list.set(index, newObject);
    }

    // Get
    // Gets the element found at the specified position
    // Throws an exception if specified index is invalid
    @Override
    public T get(int index) throws MyListException {

        // Check the specified index
        if( index < 0 || index > this.size())
            throw new MyListException("Invalid index for Getter");

        // Get the object at the specified index
        return list.get(index);
    }

    // Get Content
    // Returns the content of the list as a List Object
    @Override
    public List<T> getContent() { return this.list; }

    // Set Content
    // Sets the content of the list to the new contents
    @Override
    public void setContent(List<T> newContent) { this.list = newContent; }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() { return list.isEmpty(); }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() { return list.size(); }

    // String Formatting
    @Override
    public String toString() {

        // Creating the String format of the Stack
        // Adding Start
        StringBuilder state = new StringBuilder("{");

        // Adding all elements inside the list
        for(T elem: list) {
            state.append(elem.toString());
            state.append(",");
        }

        // Removing last extra ","
        if (!list.isEmpty()) {
            state.deleteCharAt(state.length() - 1);
        }

        // Adding End
        state.append("}");

        // Returning the State
        return state.toString();
    }
}
