////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;
import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.types.Type;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class NopStmt implements IStmt {

    // NOP STATEMENT METHODS
    // To String Method
    @Override
    public String toString() {
        return "Nop Statement";
    }

    // Executes the statement of the program defined by Program State
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException {

        // Return null
        return null;
    }

    // Returns a copy of the type
    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    // Typechecking mechanism
    // Ensure statement can be run
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        return typeEnv;
    }
}
