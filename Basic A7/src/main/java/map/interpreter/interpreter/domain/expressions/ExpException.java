////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.expressions;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class ExpException extends Exception {

    // EXPRESSION EXCEPTION METHODS
    // Default Exception Constructor
    ExpException() {
        super();
    }

    // Message Exception Constructor
    ExpException(String message) {
        super(message);
    }
}
