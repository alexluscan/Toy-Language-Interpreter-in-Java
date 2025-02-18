////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.expressions;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.state.IHeap;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.RefType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.RefValue;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class HeapReadExp implements Exp {

    // HEAP READ EXPRESSION STRUCTURE
    Exp expression;

    // HEAP READ EXPRESSION CONSTRUCTOR
    public HeapReadExp(Exp exp) { this.expression = exp; }

    // HEAP READ EXPRESSION METHODS
    // To String Method
    @Override
    public String toString() {
        return "rH(" + this.expression.toString() + ")";
    }
    @Override
    public Value eval(ISymTable symbolsTable, IHeap heap) throws ExpException {

        // Evaluate the expression
        Value expVal = expression.eval(symbolsTable, heap);
        if(!(expVal.getType() instanceof RefType))
            throw new ExpException("HEAP READ ERROR - The expression is not of reference type");

        // Safely cast to RefValue
        RefValue refVal = (RefValue) expVal;

        // Get the address in the heap of the value
        int address = refVal.getAddress();

        // Check that address is part of the heap
        if(!heap.containsKey(address))
            throw new ExpException("HEAP READ ERROR - The specified address is not allocated in the heap");

        // Return the value associated to the address
        try {
            return heap.get(address);
        }catch (MyDictionaryException exp) {
            throw new ExpException("HEAP READ ERROR - The specified address is not allocated in the heap");
        }
    }

    // Returns a copy of the expression
    @Override
    public Exp deepCopy() {
        return new HeapReadExp(this.expression.deepCopy());
    }

    // Typechecking mechanism
    // Returns the return type of the expression
    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpException {

        // Get the type of the composing expression
        Type type = expression.typeCheck(typeEnv);

        // Operand must be of RefType
        if(!(type instanceof RefType)) {
            throw new ExpException("READ HEAP EXPRESSION ERROR - Operand must be of RefType");
        }

        // Read Heap expressions returns the contained inner type
        RefType refType = (RefType) type;
        return refType.getInner();
    }
}
