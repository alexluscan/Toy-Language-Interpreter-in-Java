////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;
import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.types.Type;


///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface IStmt {

    // Executes the statement of the program defined by Program State
    PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException;

    // Returns a copy of the statement
    IStmt deepCopy();

    // Typechecking mechanism
    // Ensure statement can be run
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException;
}
