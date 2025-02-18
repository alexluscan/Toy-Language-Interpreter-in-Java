////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain;

import antonio.interpreter.interpreter.domain.datastructures.dictionary.*;
import antonio.interpreter.interpreter.domain.datastructures.list.*;
import antonio.interpreter.interpreter.domain.datastructures.stack.*;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.ExpException;
import antonio.interpreter.interpreter.domain.state.*;
import antonio.interpreter.interpreter.domain.statements.*;
import antonio.interpreter.interpreter.domain.values.*;
import map.interpreter.interpreter.domain.state.*;
import map.interpreter.interpreter.domain.statements.IStmt;
import map.interpreter.interpreter.domain.statements.StmtException;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class PrgState {

    // PROGRAM STATE STRUCTURE
    // Static ID for all Program States
    static Integer id = 0;

    // Program State Attributes
    // Program ID
    Integer programID;
    // Execution Stack
    IExeStack executionStack;
    // Symbols Table Dictionary
    ISymTable symbolsTable;
    // Output List
    IOutList outputList;
    // File Table Dictionary
    IFileTable fileTable;
    // Heap Map
    IHeap heap;
    // We also need to keep track of the original program state
    IStmt originalProgram;

    // PROGRAM STATE CONSTRUCTOR
    public PrgState(IExeStack stack, ISymTable symTable, IOutList outputs, IFileTable fileTable, IHeap heap, IStmt prg){

        // Set the Program State Attributes
        this.executionStack = stack;
        this.symbolsTable = symTable;
        this.outputList = outputs;
        this.fileTable = fileTable;
        this.heap = heap;
        this.programID = getNextId();

        // Keep track of the original Program State
        // Recreate the entire original Program State
        originalProgram=deepCopy(prg);

        // Add the first statement on the Execution Stack
        executionStack.push(prg);
    }


    // PROGRAM STATE METHODS
    // Private Helper Methods
    private IStmt deepCopy(IStmt prg) {

        return prg.deepCopy();
    }

    // Public Methods
    // Getters
    public Integer getProgramID() { return this.programID; }
    public IExeStack getExecutionStack() {
        return this.executionStack;
    }
    public ISymTable getSymbolsTable() {
        return this.symbolsTable;
    }
    public IOutList getOutputList() {return this.outputList; }
    public IFileTable getFileTable() { return this.fileTable; }
    public IHeap getHeap() { return this.heap; }

    // Setters
    public void setExecutionStack(IExeStack newExecutionStack) {
        this.executionStack = newExecutionStack;
    }
    public void setSymbolsTable(ISymTable newSymTable) {
        this.symbolsTable = newSymTable;
    }
    public void setOutputList(IOutList newOutputList) {
        this.outputList = newOutputList;
    }
    public void setFileTable(IFileTable newFileTable) { this.fileTable = newFileTable; }
    public void setHeap(IHeap heap) { this.heap = heap; }

    // State Information Getters
    public Boolean isNotCompleted() { return !executionStack.isEmpty(); }

    // State Information Setters
    public static synchronized int getNextId() {
        return ++id;
    }

    // State Execution
    public PrgState oneStep() throws StmtException, ExpException, MyDictionaryException, PrgStateException, MyStackException {

        // Check if there are statements left to execute
        if(this.getExecutionStack().isEmpty()){
            throw new PrgStateException("Program State Error - Execution Stack is Empty");
        }

        // Execute one statement
        IStmt currentStatement = this.getExecutionStack().pop();
        return currentStatement.execute(this);
    }

    // String Formatting
    @Override
    public String toString() {

        // Creating the string format of the state
        StringBuilder state = new StringBuilder();

        // Id
        state.append("ID = ");
        state.append(programID.toString());
        state.append("\n");

        // Execution Stack
        state.append("Execution Stack = ");
        state.append(executionStack.toString());
        state.append("\n");

        // Symbols Table
        state.append("Symbols Table = ");
        state.append(symbolsTable.toString());
        state.append("\n");

        // Output List
        state.append("Output List = ");
        state.append(outputList.toString());
        state.append("\n");

        // File Table
        state.append("File Table = ");
        state.append(fileTable.toString());
        state.append("\n");

        // Heap
        state.append("Heap = ");
        state.append(heap.toString());
        state.append("\n");

        state.append("====================>");
        state.append("\n");

        // Returning the state as String
        return state.toString();
    }

    // String Logging Formatting
    public String toLogString() {
        // Creating the string format of the state
        StringBuilder state = new StringBuilder();

        // Id
        state.append("ID = ");
        state.append(programID.toString());
        state.append("\n");

        // Execution Stack
        state.append("Execution Stack = \n");
        state.append(executionStack.toFString());
        state.append("\n");

        // Symbols Table
        state.append("Symbols Table = ");
        state.append(symbolsTable.toString());
        state.append("\n");

        // Output List
        state.append("Output List = ");
        state.append(outputList.toString());
        state.append("\n");

        // File Table
        state.append("File Table = ");
        state.append(fileTable.toString());
        state.append("\n");

        // Heap
        state.append("Heap = ");
        state.append(heap.toString());
        state.append("\n");

        state.append("====================>");
        state.append("\n\n\n");

        // Returning the state as String
        return state.toString();
    }
}
