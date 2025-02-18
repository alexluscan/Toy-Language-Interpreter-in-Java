////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class PrgStateException extends Exception {

    // PROGRAM STATE EXCEPTION METHODS
    // Default Exception Constructor
    PrgStateException() {
        super();
    }

    // Message Exception Constructor
    PrgStateException(String message) {
        super(message);
    }
}
