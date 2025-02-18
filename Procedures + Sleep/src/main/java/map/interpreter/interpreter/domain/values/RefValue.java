////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.values;
import map.interpreter.interpreter.domain.types.RefType;
import map.interpreter.interpreter.domain.types.Type;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class RefValue implements Value {

    // REF VALUE STRUCTURE
    // Based on a heap-address and the type of the location on that address
    Integer address;
    Type locationType;


    // REF VALUE CONSTRUCTOR
    public RefValue(int address, Type locationType) { this.address=address; this.locationType=locationType; }


    // REF VALUE METHODS
    // Getter Methods
    // Returns the address inside the heap memory
    public Integer getAddress() { return this.address; }

    // Returns the type of the location inside the heap memory
    public Type getLocationType() { return locationType; }

    // Returns the type of the value - RefType
    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    // Returns a copy of the value
    @Override
    public Value deepCopy() {
        return new RefValue(this.address, locationType.deepCopy());
    }

    // Returns a string form of the integer value stored
    @Override
    public String toString() { return "(" + this.address + ", " +  this.locationType.toString() + ")"; }

}
