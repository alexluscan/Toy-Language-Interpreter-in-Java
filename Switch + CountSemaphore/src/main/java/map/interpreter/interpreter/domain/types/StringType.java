////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.types;


import map.interpreter.interpreter.domain.values.StringValue;
import map.interpreter.interpreter.domain.values.Value;

//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class StringType implements Type {

    // STRING TYPE METHODS
    // Returns True if the other object is of instance StringType
    @Override
    public boolean equals(Object other){
        return other instanceof StringType;
    }

    // Returns a string format for the IntType
    @Override
    public String toString() { return "String";}

    // Returns the default value of a string type
    @Override
    public Value defaultValue() {
        return new StringValue("\"\"");
    }

    // Returns a copy of the type
    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
