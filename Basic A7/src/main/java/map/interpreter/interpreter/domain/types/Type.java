////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.types;
import map.interpreter.interpreter.domain.values.Value;


///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface Type {

    // Returns the default value of a given type
    Value defaultValue();

    // Returns a copy of the given type
    Type deepCopy();
}
