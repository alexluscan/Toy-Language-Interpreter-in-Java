////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.values;

import map.interpreter.interpreter.domain.types.StringType;
import map.interpreter.interpreter.domain.types.Type;

public class StringValue implements Value {

    // STRING VALUE STRUCTURE
    // Based on a string
    String value;


    // STRING VALUE CONSTRUCTORS
    public StringValue(String v) { value = v; }


    // STRING VALUE METHODS
    // Returns the string value stored
    public String getValue() { return value; }

    // Returns a string form of the integer value stored - actual content
    @Override
    public String toString() { return value; }

    // Returns true if the other object's type is StringValue
    @Override
    public boolean equals(Object other) {

        // Check the type of other object
        if(!(other instanceof StringValue otherValueType))
            return false;

        // Safely cast to StringValue
        String otherValue = otherValueType.getValue();

        // Check equality and return
        return otherValue.equals(this.value);
    }

    // Returns the type of the value - StringType
    @Override
    public Type getType() {
        return new StringType();
    }

    // Returns a copy of the value
    @Override
    public Value deepCopy() {
        return new StringValue(this.value);
    }
}
