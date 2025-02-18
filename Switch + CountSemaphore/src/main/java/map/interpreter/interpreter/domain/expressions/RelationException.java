////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.expressions;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class RelationException extends ExpException {

    // RELATIONAL EXPRESSION EXCEPTION METHODS
    // Default Exception Constructor
    RelationException() {
        super();
    }

    // Message Exception Constructor
    RelationException(String message) {
        super(message);
    }
}
