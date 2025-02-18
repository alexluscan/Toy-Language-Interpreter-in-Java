////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.list.MyListException;
import map.interpreter.interpreter.domain.datastructures.stack.MyIStack;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.ExpException;
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
    // Symbols Tables Stack
    MyIStack<ISymTable> symbolsTables;
    // Output List
    IOutList outputList;
    // File Table Dictionary
    IFileTable fileTable;
    // Heap Map
    IHeap heap;
    // Proc Table
    IProcTable procTable;
    // We also need to keep track of the original program state
    IStmt originalProgram;

    // PROGRAM STATE CONSTRUCTOR
    public PrgState(IExeStack stack, MyIStack<ISymTable> symTables, IOutList outputs, IFileTable fileTable, IHeap heap, IStmt prg, IProcTable procTable){

        // Set the Program State Attributes
        this.executionStack = stack;
        this.symbolsTables = symTables;
        this.outputList = outputs;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procTable = procTable;
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
    public ISymTable getSymbolsTable() throws MyStackException {
        return this.symbolsTables.top();
    }
    public MyIStack<ISymTable> getSymbolsTableStack() {
        return this.symbolsTables;
    }
    public IOutList getOutputList() {return this.outputList; }
    public IFileTable getFileTable() { return this.fileTable; }
    public IProcTable getProcTable() { return this.procTable; }
    public IHeap getHeap() { return this.heap; }

    // Setters
    public void setExecutionStack(IExeStack newExecutionStack) {
        this.executionStack = newExecutionStack;
    }
    public void setSymbolsTable(MyIStack<ISymTable> newSymTables) {
        this.symbolsTables = newSymTables;
    }
    public void setOutputList(IOutList newOutputList) {
        this.outputList = newOutputList;
    }
    public void setFileTable(IFileTable newFileTable) { this.fileTable = newFileTable; }
    public void setHeap(IHeap heap) { this.heap = heap; }
    public void setProcTable(IProcTable procTable) { this.procTable = procTable; }

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
        try {
            return currentStatement.execute(this);
        } catch (MyListException e) {
            throw new RuntimeException(e);
        }
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
        state.append("Symbols Table Stack = ");
        state.append(symbolsTables.toString());
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

        // Proc Table
        state.append("Proc Table = ");
        state.append(procTable.toString());
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
        state.append("Symbols Table Stack = ");
        state.append(symbolsTables.toString());
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

        // Proc Table
        state.append("Proc Table = ");
        state.append(procTable.toString());
        state.append("\n");

        state.append("====================>");
        state.append("\n\n\n");

        // Returning the state as String
        return state.toString();
    }
}
