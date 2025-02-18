////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.types;
import map.interpreter.interpreter.domain.values.IntValue;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class IntType implements Type {

    // INT TYPE METHODS
    // Returns True if the other object is of instance IntType
    @Override
    public boolean equals(Object other){
        return other instanceof IntType;
    }

    // Returns a string format for the IntType
    @Override
    public String toString() { return "int";}

    // Returns the default value of a integer type
    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    // Returns a copy of the type
    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
