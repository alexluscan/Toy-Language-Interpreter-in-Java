////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.datastructures.stack;


import java.util.List;

///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface MyIStack<T> {

    // MY I STACK METHODS

    // Push
    // Pushes a new element on the top of the stack
    void push(T v);

    // Pop
    // Removes and returns the top of the stack
    // Throws an exception if the stack is empty
    T pop() throws MyStackException;

    // Top
    // Returns the top of the stack
    // Throws an exception if the stack is empty
    T top() throws MyStackException;

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    boolean isEmpty();

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    int size();

    // To List
    // Gets the stack as a list
    List<T> toList();
}
