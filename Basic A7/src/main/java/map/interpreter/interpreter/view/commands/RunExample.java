////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.view.commands;

import map.interpreter.interpreter.controller.BasicController;
import map.interpreter.interpreter.controller.Controller;
import map.interpreter.interpreter.controller.ControllerException;
import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionary;
import antonio.interpreter.interpreter.domain.state.*;
import map.interpreter.interpreter.domain.state.*;
import map.interpreter.interpreter.domain.statements.IStmt;
import map.interpreter.interpreter.domain.statements.StmtException;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class RunExample extends Command {

    // COMMAND CLASS STRUCTURE
    // A run example command is based on a controller to execute the example
    private IStmt initialProgram;
    private final String logFilePath;

    // COMMAND CLASS CONSTRUCTOR
    public RunExample(String key, String desc, String logFilePath, IStmt initialProgram){
        super(key, desc);
        this.initialProgram=initialProgram;
        this.logFilePath = logFilePath;
    }

    // COMMAND CLASS METHODS
    // Executes the command object
    // Executes all steps of the current controller
    @Override
    public void execute() {
        try{

            // Type Checking
            this.initialProgram.typeCheck(new MyDictionary<>());

            // Creating program
            PrgState prg = new PrgState(new ExeStack(), new SymTable(), new OutList(), new FileTable(), new Heap(), this.initialProgram);
            Controller controller = new BasicController(1, logFilePath, prg);

            // Execute the program
            controller.allStep();
        }
        catch (ControllerException e)  {
            System.out.println("There was an error running the current program: " + e);
        }
        catch (StmtException e) {
            System.out.println("There was an error typechecking the current program: " + e);
        }
    }
}
