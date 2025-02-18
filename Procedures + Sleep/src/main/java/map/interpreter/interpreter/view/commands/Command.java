////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.view.commands;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public abstract class Command {

    // COMMAND CLASS STRUCTURE
    // A command is based on its key and description (strings)
    private String key, description;


    // COMMAND CLASS CONSTRUCTOR
    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }


    // COMMAND CLASS METHODS
    // Getters
    public String getKey() { return key; }
    public String getDescription() { return description; }

    // Executes the command object
    public abstract void execute();
}
