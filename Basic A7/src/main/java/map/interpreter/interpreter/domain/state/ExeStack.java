////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.state;
import map.interpreter.interpreter.domain.datastructures.stack.MyIStack;
import map.interpreter.interpreter.domain.datastructures.stack.MyStack;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.statements.IStmt;
import map.interpreter.interpreter.domain.utils.StmtParsing;

import java.util.ArrayList;
import java.util.List;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class ExeStack implements IExeStack {

    // I EXE STACK STRUCTURE
    MyIStack<IStmt> executionStack;

    // I EXE STACK CONSTRUCTORS
    public ExeStack(){ this.executionStack = new MyStack<>(); }

    // I EXE STACK METHODS
    // Push
    // Pushes a new element on the top of the stack
    @Override
    public void push(IStmt v) { this.executionStack.push(v); }

    // Pop
    // Removes and returns the top of the stack
    // Throws an exception if the stack is empty
    @Override
    public IStmt pop() throws MyStackException { return this.executionStack.pop(); }

    // Top
    // Returns the top of the stack
    // Throws an exception if the stack is empty
    @Override
    public IStmt top() throws MyStackException { return this.executionStack.top(); }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() { return this.executionStack.isEmpty(); }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() { return this.executionStack.size(); }

    // String Formatting
    @Override
    public String toString() { return this.executionStack.toString(); }


    // Deep Copy
    @Override
    public IExeStack deepCopy() {

        IExeStack newStack = new ExeStack();
        MyIStack<IStmt> tempStack = new MyStack<>();

        // Move all elements from the current stack to the temporary stack
        while (!this.executionStack.isEmpty()) {
            try {
                IStmt stmt = this.executionStack.pop();
                tempStack.push(stmt);
            } catch (MyStackException e) {
                throw new RuntimeException("Error during deep copy: " + e.getMessage());
            }
        }

        // Restore the original stack and populate the new stack in the same order
        while (!tempStack.isEmpty()) {
            try {
                IStmt stmt = tempStack.pop();
                this.executionStack.push(stmt);
                newStack.push(stmt.deepCopy());
            } catch (MyStackException e) {
                throw new RuntimeException("Error during deep copy: " + e.getMessage());
            }
        }

        return newStack;
    }

    // To List
    // Gets the execution stack as a list
    @Override
    public List<IStmt> toList() {
        List<IStmt> result = new ArrayList<>();

        // Use a copy of the execution stack to preserve the original
        IExeStack tempStack = this.deepCopy();

        while (!tempStack.isEmpty()) {
            try {

                // Perform infix traversal starting from the current statement
                IStmt root = tempStack.pop();
                StmtParsing.infixTraversal(root, result);

            } catch (MyStackException e) {
                throw new RuntimeException("Error during infix traversal: " + e.getMessage());
            }
        }

        return result;
    }


    // To Formatted String
    // Gets the execution stack list as a string
    @Override
    public String toFString() {

        // Check if the execution stack is empty
        if(this.executionStack.isEmpty()) {
            return "(EMPTY)";
        }

        // Add all statements on a new line
        List<IStmt> statements = this.toList();
        StringBuilder stringBuilder = new StringBuilder();
        for(IStmt statement : statements) {
            stringBuilder.append(statement.toString());
            stringBuilder.append("\n");
        }

        // Return the built string
        return stringBuilder.toString();
    }
}
