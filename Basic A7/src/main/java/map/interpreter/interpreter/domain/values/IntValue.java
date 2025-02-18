////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.values;
import map.interpreter.interpreter.domain.types.IntType;
import map.interpreter.interpreter.domain.types.Type;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class IntValue implements Value {

    // INT VALUE STRUCTURE
    // Based on an integer
    Integer value;


    // INT VALUE CONSTRUCTORS
    public IntValue(Integer v) { value = v; }


    // INT VALUE METHODS
    // Returns the integer value stored
    public Integer getValue() { return value; }

    // Returns a string form of the integer value stored
    @Override
    public String toString() { return value.toString(); }

    // Returns true if the other object's type is IntValue
    @Override
    public boolean equals(Object other) {

        // Check the type of other object
        if(!(other instanceof IntValue otherValueType))
            return false;

        // Safely cast to IntValue
        Integer otherValue = otherValueType.getValue();

        // Check equality and return
        return otherValue.equals(this.value);
    }

    // Returns the type of the value - Integer
    @Override
    public Type getType() {
        return new IntType();
    }

    // Returns a copy of the value
    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }
}
