////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.state.IExeStack;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class AssignStmt implements IStmt {

    // ASSIGNMENT STATEMENT STRUCTURE
    // An Assignment Statement is formed of an expression to print
    String id;
    Exp expression;


    // ASSIGNMENT STATEMENT CONSTRUCTORS
    public AssignStmt(String v, Exp e) {
        id = v;
        expression = e;
    }

    // ASSIGNMENT STATEMENT METHODS
    // To String Method
    @Override
    public String toString() {
        return id +"=" + expression.toString();
    }

    // Executes the statement of the program defined by Program State
    @Override
    public PrgState execute(PrgState state) throws StmtException, MyDictionaryException, ExpException {

        // Get the current stack and symbols table
        IExeStack stk = state.getExecutionStack();
        ISymTable symTbl = state.getSymbolsTable();

        // Check if the variable name is in the symbols table
        if(!symTbl.containsKey(id))
            throw new StmtException("The used variable " + id + " was not declared before");

        // Match the type of the new value to that of the old value for type compatibility
        Value val = expression.eval(symTbl, state.getHeap());
        Type typId= (symTbl.get(id)).getType();
        if(!val.getType().equals(typId))
            throw new StmtException("Declared type of variable " + id + " and type of the assigned expression do not match");

        // Update the variable in the symbolic table
        symTbl.update(id, val);

        // Return null
        return null;
    }

    // Returns a copy of the type
    @Override
    public IStmt deepCopy() {
        return new AssignStmt(this.id, this.expression.deepCopy());
    }

    // Typechecking mechanism
    // Ensure statement can be run
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try{
            // Get the types of the composing variable and expression
            Type typeVar = typeEnv.get(id);
            Type typeExp = expression.typeCheck(typeEnv);

            // The two types must correspond
            if(!typeVar.equals(typeExp)){
                throw new StmtException("ASSIGNMENT STATEMENT ERROR - The type of the variable and of the expression must correspond");
            }

            // Return the typechecking dictionary
            return typeEnv;
        } catch (MyDictionaryException | ExpException exp) {
            throw new StmtException("ASSIGNMENT STATEMENT ERROR - There was an error evaluating the statement: " + exp);
        }

    }
}
