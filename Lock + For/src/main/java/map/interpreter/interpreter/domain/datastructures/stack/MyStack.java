////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.datastructures.stack;

import java.util.LinkedList;
import java.util.List;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class MyStack<T> implements MyIStack<T> {

    // MY STACK STRUCTURE
    // Stack - based on linked list
    LinkedList<T> stack;


    // MY STACK CONSTRUCTOR
    public MyStack() {
        stack = new LinkedList<>();
    }


    // MY STACK METHODS
    // Top
    // Returns the top of the stack
    // Throws an exception if the stack is empty
    @Override
    public T top() throws MyStackException {

        // Check if stack has a top (contains elements)
        if(this.isEmpty())
            throw new MyStackException("Stack is empty");

        // Returns the top of the stack
        return stack.getFirst();
    }

    // Pop
    // Removes and returns the top of the stack
    // Throws an exception if the stack is empty
    @Override
    public T pop() throws MyStackException {

        // Check if stack has a top (contains elements)
        if(this.isEmpty())
            throw new MyStackException("Stack is empty");

        // Pop the top of the stack
        return stack.pop();
    }

    // Push
    // Pushes a new element on the top of the stack
    @Override
    public void push(T v) {
        stack.push(v);
    }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() { return stack.isEmpty(); }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() { return stack.size(); }

    // String Formatting
    @Override
    public String toString() {

        // Creating the String format of the Stack
        // Adding Start
        StringBuilder state = new StringBuilder("{");

        // Adding all elements inside the stack
        for(T elem: stack) {
            state.append(elem.toString());
            state.append("|");
        }

        // Removing last extra "|"
        if (!stack.isEmpty()) {
            state.deleteCharAt(state.length() - 1);
        }

        // Adding End
        state.append("}");

        // Returning the State
        return state.toString();
    }

    // To List
    // Gets the execution stack as a list
    @Override
    public List<T> toList() {
        return stack.stream().toList();
    }
}
