////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.values;
import map.interpreter.interpreter.domain.types.Type;


///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface Value {

    // Returns the type of the value
    Type getType();

    // Returns a copy of the value
    Value deepCopy();
}
