////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.types;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class TypeException extends Exception {

    // TYPE EXCEPTION METHODS
    // Default Exception Constructor
    TypeException() {
        super();
    }

    // Message Exception Constructor
    TypeException(String message) {
        super(message);
    }
}
