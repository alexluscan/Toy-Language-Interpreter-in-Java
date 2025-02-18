package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.types.IntType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.IntValue;
import map.interpreter.interpreter.domain.values.Value;

public class UnlockStmt implements IStmt {
    // UNLOCK STATEMENT STRUCTURE
    String variableName;

    // UNLOCK STATEMENT CONSTRUCTOR
    public UnlockStmt(String variableName) {
        this.variableName = variableName;
    }

    // UNLOCK STATEMENT METHODS
    @Override
    public String toString() {
        return "unlock("+variableName+")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException, MyStackException {

        // Looking up in symbols table
        if(!state.getSymbolsTable().containsKey(variableName) || !state.getSymbolsTable().get(variableName).getType().equals(new IntType()))
            throw new StmtException("LOCK STATEMENT ERROR - Variable does not exist in the SymTable or is not of type Integer");

        Value foundIndexValue = state.getSymbolsTable().get(variableName);
        Integer foundIndex = ((IntValue) foundIndexValue).getValue();

        // Looking up in lock table
        if(!state.getLockTable().containsKey(foundIndex))
            throw new StmtException("LOCK STATEMENT ERROR - Variable does not exist in the LockTable");

        // Checking Lock status
        if(state.getLockTable().get(foundIndex).equals(state.getProgramID())) {

            // Release Lock
            state.getLockTable().update(foundIndex, -1);
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(variableName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try{
            if(!typeEnv.get(variableName).equals(new IntType()))
                throw new StmtException("UNLOCK STATEMENT ERROR - Variable type must be Integer");
            return typeEnv;
        } catch (Exception exc) {
            throw new StmtException("UNLOCK STATEMENT ERROR - " + exc);
        }
    }
}
