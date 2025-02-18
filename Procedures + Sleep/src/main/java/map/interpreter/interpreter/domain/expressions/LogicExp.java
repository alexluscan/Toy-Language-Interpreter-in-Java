////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.expressions;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.state.IHeap;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.BoolType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.BoolValue;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class LogicExp implements Exp {

    // LOGIC EXPRESSION STRUCTURE
    // Based on two expression operands & operator
    Exp e1, e2;
    // Option can be: AND (1), OR (2)
    int option;
    String optionChar;

    // LOGIC EXPRESSION CONSTRUCTORS
    LogicExp(Exp firstExpression, Exp secondExpression, int op) {
        e1 = firstExpression;
        e2 = secondExpression;
        option = op;

        switch (op){
            case 1: optionChar="and"; break;
            case 2: optionChar="or"; break;
        }
    }

    // LOGIC EXPRESSION METHODS
    // String Formatting
    @Override
    public String toString() {
        return e1.toString() + " " + optionChar + " " + e2.toString();
    }

    // Evaluates the given expression given the values in symbolsTable
    @Override
    public Value eval(ISymTable symbolsTable, IHeap heap) throws ExpException {

        // Evaluate the expressions for the operands
        Value v1, v2;
        v1 = e1.eval(symbolsTable, heap);
        v2 = e2.eval(symbolsTable, heap);

        // Check type of the operands
        // Must be booleans
        if(!v1.getType().equals(new BoolType()) || !v2.getType().equals(new BoolType())) {
            throw new LogicException("Both operands must be of type Boolean");
        }

        // Safely cast to BoolType
        BoolValue b1 = (BoolValue) v1;
        BoolValue b2 = (BoolValue) v2;

        // Get final integer values
        Boolean n1 = b1.getValue();
        Boolean n2 = b2.getValue();

        // Return the result of the operation
        switch (option) {

            // Common Operations
            case 1: return new BoolValue(n1 && n2);
            case 2: return new BoolValue(n1 || n2);

            // Operation not found
            default: throw new LogicException("Operation type not specified or not correct");
        }
    }

    // Returns a copy of the type
    @Override
    public Exp deepCopy() {
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), this.option);
    }

    // Typechecking mechanism
    // Returns the return type of the expression
    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpException {

        // Get the type of the two composing expressions
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);

        // Both operands must be of type Boolean
        if((!type1.equals(new BoolType())) || (!type2.equals(new BoolType()))) {
            throw new ExpException("LOGIC EXPRESSION ERROR - Both operands must be of boolean type");
        }

        // Logic expressions return a Boolean type
        return new BoolType();
    }
}
