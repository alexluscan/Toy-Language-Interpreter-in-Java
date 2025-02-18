////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;
import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.state.IHeap;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.RefType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.RefValue;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class HeapAllocStmt implements IStmt {

    // HEAP ALLOCATION STATEMENT STRUCTURE
    String variableName;
    Exp expression;


    // HEAP ALLOCATION STATEMENT CONSTRUCTOR
    public HeapAllocStmt(String variableName, Exp expression) { this.variableName=variableName; this.expression=expression; }


    // HEAP ALLOCATION STATEMENT METHODS
    // To String Method
    @Override
    public String toString() {
        return "new("+ variableName + ", " + this.expression.toString() + ")";
    }

    // Executes the statement of the program defined by Program State
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException {

        // Get the current program state
        IHeap heap = state.getHeap();
        ISymTable symTbl = state.getSymbolsTable();

        // Check if the variable name is in the symbols table
        if(!symTbl.containsKey(variableName))
            throw new StmtException("HEAP ALLOCATION ERROR - The variable was not declared");

        // Check type of the value
        // Must be RefType
        Value val = symTbl.get(variableName);
        if(!(val.getType() instanceof RefType))
            throw new StmtException("HEAP ALLOCATION ERROR - Variable is not of reference type");

        // Safely cast to RefValue
        RefValue refVal = (RefValue) val;

        // Evaluate new expression
        Value expVal = expression.eval(symTbl, heap);
        if(!expVal.getType().equals(refVal.getLocationType()))
            throw new StmtException("HEAP ALLOCATION ERROR - The expression does not match variable inner type");

        // Appending to the heap
        int address = heap.put(expVal);

        // Updating the symbols table
        symTbl.update(variableName, new RefValue(address, refVal.getLocationType()));

        // Return null
        return null;
    }

    // Returns a copy of the statement
    @Override
    public IStmt deepCopy() {
        return new HeapAllocStmt(variableName, expression.deepCopy());
    }

    // Typechecking mechanism
    // Ensure statement can be run
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try {
            // Check the type of the variable and of the expression
            Type typeVar = typeEnv.get(variableName);
            Type typeExp = expression.typeCheck(typeEnv);

            // The type of the variable must be a reference to the type of the expression
            if(!typeVar.equals(new RefType(typeExp))) {
                throw new StmtException("HEAP ALLOCATION STATEMENT ERROR - The type of the variable must be RefType( type of expression )");
            }

            // Return the typechecking dictionary
            return typeEnv;

        } catch(MyDictionaryException | ExpException exp) {
            throw new StmtException("HEAP ALLOCATION STATEMENT ERROR - " + exp);
        }
    }
}
