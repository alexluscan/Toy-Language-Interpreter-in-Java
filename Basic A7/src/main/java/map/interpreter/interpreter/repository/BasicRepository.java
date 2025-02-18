////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.repository;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.list.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class BasicRepository implements Repository {

    // BASIC REPOSITORY STRUCTURE
    // The basic repository is based on a list of PrgStates
    MyIList<PrgState> programStates;
    String logFilePath;


    // BASIC REPOSITORY CONSTRUCTOR
    public BasicRepository(String logFilePath, PrgState prgState) {
        this.programStates = new MyList<>();
        this.programStates.add(prgState);

        this.logFilePath = logFilePath;
    }


    // BASIC REPOSITORY METHODS
    // Gets the number of all programs
    @Override
    public Integer getPrgListCount() { return  this.programStates.size(); }

    // Gets the list of all programs
    @Override
    public List<PrgState> getPrgList() {
        return this.programStates.getContent();
    }

    // Sets the list of all programs
    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.programStates.setContent(prgList);
    }

    // Clear log file
    public void clearLogFile() throws RepositoryException {
        try (FileWriter fileWriter = new FileWriter(this.logFilePath, false)) {

            // Opening the file in overwrite mode with no content will clear it.
            // Clear the file by writing an empty string.
            fileWriter.write("");

        } catch (IOException e) {
            throw new RepositoryException("There was an error clearing the log file: " + e);
        }
    }

    // Logs Repository state to a file
    public void logPrgStateExec(PrgState state) throws RepositoryException {

        try {

            // Create output stream
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));

            // Write the contents of the current program
            logFile.write(state.toLogString());

            // Close output stream
            logFile.close();

        } catch (IOException e) {
            throw new RepositoryException("There was an error logging data about the currently running program: " + e);
        }

    }
}
