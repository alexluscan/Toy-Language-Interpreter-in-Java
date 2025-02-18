////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.types;
import map.interpreter.interpreter.domain.values.RefValue;
import map.interpreter.interpreter.domain.values.Value;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class RefType implements Type {

    // REF TYPE STRUCTURE
    // Based on another inner structure
    Type inner;


    // REF TYPE CONSTRUCTOR
    public RefType(Type inner) {
        this.inner = inner;
    }


    // REF TYPE METHODS
    // Getter Methods
    // Gets the inner type of the Ref Type
    public Type getInner() { return this.inner; }

    // Returns True if the other object is of instance RefType and inner has the same type
    @Override
    public boolean equals(Object other){
        if( other instanceof RefType)
            return inner.equals(((RefType) other).getInner());
        return false;
    }

    // Returns a string format for the RefType
    @Override
    public String toString() { return "Ref(" + this.inner.toString() + ")";}

    // Returns the default value of the ref type with inner value
    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    // Returns a copy of the type
    @Override
    public Type deepCopy() {
        return new RefType(inner);
    }

}
