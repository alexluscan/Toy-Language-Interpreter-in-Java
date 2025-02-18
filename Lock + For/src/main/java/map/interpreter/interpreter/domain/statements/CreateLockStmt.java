package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.types.IntType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.IntValue;

public class CreateLockStmt implements IStmt {

    // CREATE LOCK STATEMENT STRUCTURE
    String variableName;

    // CREATE LOCK STATEMENT CONSTRUCTOR
    public CreateLockStmt(String variableName) { this.variableName = variableName; }

    // CREATE LOCK STATEMENT METHODS
    @Override
    public String toString() {
        return "newLock("+variableName+")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException, MyStackException {

        // Adding lock to the table
        Integer newFreeLocation = state.getLockTable().put(-1);

        // Adding into symbols table
        if(!state.getSymbolsTable().containsKey(variableName) || !state.getSymbolsTable().get(variableName).getType().equals(new IntType()))
            throw new StmtException("CREATE LOCK STATEMENT ERROR - Variable does not exist in the SymTable or is not of type Integer");

        state.getSymbolsTable().update(variableName, new IntValue(newFreeLocation));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateLockStmt(variableName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try{
            if(!typeEnv.get(variableName).equals(new IntType()))
                throw new StmtException("CREATE LOCK STATEMENT ERROR - Variable type must be Integer");
            return typeEnv;
        } catch (Exception exc) {
            throw new StmtException("CREATE LOCK STATEMENT ERROR - " + exc);
        }
    }
}
