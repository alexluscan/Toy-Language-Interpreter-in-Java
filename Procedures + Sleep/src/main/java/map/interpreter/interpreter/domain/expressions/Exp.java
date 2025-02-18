////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.expressions;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.state.IHeap;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.Value;


///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface Exp {

    // Evaluates the given expression given the values in symbolsTable
    Value eval(ISymTable symbolsTable, IHeap heap) throws ExpException;

    // Returns a copy of the expression
    Exp deepCopy();

    // Typechecking mechanism
    // Returns the return type of the expression
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpException;
}
