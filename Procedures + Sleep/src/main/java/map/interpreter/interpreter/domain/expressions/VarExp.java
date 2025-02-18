////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.expressions;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.state.IHeap;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class VarExp implements Exp {

    // VARIABLE EXPRESSION STRUCTURE
    // Based on a variable name - String
    String variableName;


    // VARIABLE EXPRESSION CONSTRUCTORS
    public VarExp(String variableName) { this.variableName = variableName; }


    // VARIABLE EXPRESSION METHODS
    // String Formatting
    @Override
    public String toString() {
        return variableName;
    }

    // Evaluates the variable given the values in symbolsTable
    @Override
    public Value eval(ISymTable symbolsTable, IHeap heap) throws ExpException {

        // Gets the value associated to the symbol in the table
        try {
            return symbolsTable.get(variableName);
        } catch (MyDictionaryException exception) {
            throw new ExpException(exception.toString());
        }
    }

    // Returns a copy of the type
    @Override
    public Exp deepCopy() {
        return new VarExp(this.variableName);
    }

    // Typechecking mechanism
    // Returns the return type of the expression
    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpException {

        // Variable expressions return the type of the associated variable
        try {
            return typeEnv.get(variableName);
        } catch (MyDictionaryException exp) {
            throw new ExpException("VARIABLE EXPRESSION ERROR - variable not found");
        }
    }
}
