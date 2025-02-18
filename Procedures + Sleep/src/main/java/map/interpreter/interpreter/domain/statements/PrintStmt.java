////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.state.IOutList;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class PrintStmt implements IStmt {

    // PRINT STATEMENT STRUCTURE
    // A Print Statement is formed of an expression to print
    Exp expression;

    public PrintStmt(Exp e) {
        expression = e;
    }

    // PRINT STATEMENT METHODS
    // To String Method
    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    // Executes the statement of the program defined by Program State
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException {

        // Get the current program state
        IOutList outputList = state.getOutputList();
        ISymTable symTbl = null;
        try {
            symTbl = state.getSymbolsTable();
        } catch (MyStackException e) {
            throw new RuntimeException(e);
        }

        // Evaluate the expression and add result to output list
        Value result = expression.eval(symTbl, state.getHeap());
        outputList.add(result);

        // Return null
        return null;
    }

    // Returns a copy of the type
    @Override
    public IStmt deepCopy() {
        return new PrintStmt(this.expression.deepCopy());
    }

    // Typechecking mechanism
    // Ensure statement can be run
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try {
            expression.typeCheck(typeEnv);
            return typeEnv;
        } catch(ExpException exp) {
            throw new StmtException("PRINT STATEMENT EXCEPTION - " + exp);
        }
    }
}
