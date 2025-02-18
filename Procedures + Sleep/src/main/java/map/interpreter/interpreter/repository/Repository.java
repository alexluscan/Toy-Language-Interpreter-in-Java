////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.repository;

import map.interpreter.interpreter.domain.PrgState;

import java.util.List;


///////////////////////////
// INTERFACE DESCRIPTION //
///////////////////////////
public interface Repository {

    // REPOSITORY METHODS
    // Gets the number of all programs
    Integer getPrgListCount();

    // Gets the list of all programs
    List<PrgState> getPrgList();

    // Sets the list of all programs
    void setPrgList(List<PrgState> prgList);

    // Log File Operations
    // Clear log file
    void clearLogFile() throws RepositoryException;

    // Logs Repository State to file
    void logPrgStateExec(PrgState state) throws RepositoryException;
}
