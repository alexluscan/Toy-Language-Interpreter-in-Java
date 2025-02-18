////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.view.commands;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class ExitCommand extends Command {

    // COMMAND CLASS CONSTRUCTOR
    public ExitCommand(String key, String description) { super(key, description); }


    // COMMAND CLASS METHODS
    // Executes the command object
    // Exits the program with status 0
    @Override
    public void execute() {
        System.exit(0);
    }
}
