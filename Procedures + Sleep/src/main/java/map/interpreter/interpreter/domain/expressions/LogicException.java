////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.expressions;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class LogicException extends ExpException{

    // LOGIC EXPRESSION EXCEPTION METHODS
    // Default Exception Constructor
    LogicException() {
        super();
    }

    // Message Exception Constructor
    LogicException(String message) {
        super(message);
    }
}
