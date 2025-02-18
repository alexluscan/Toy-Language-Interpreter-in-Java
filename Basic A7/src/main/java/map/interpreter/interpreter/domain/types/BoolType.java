////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.types;


import map.interpreter.interpreter.domain.values.BoolValue;
import map.interpreter.interpreter.domain.values.Value;

//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class BoolType implements Type {

    // BOOLEAN TYPE METHODS
    // Returns True if the other object is of instance BoolType
    @Override
    public boolean equals(Object other){
        return other instanceof BoolType;
    }

    // Returns a string format for the BoolType
    @Override
    public String toString() { return "bool";}

    // Returns the default value of a boolean type
    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    // Returns a copy of the type
    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
