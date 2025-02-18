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

public class LockStmt implements IStmt {

    // LOCK STATEMENT STRUCTURE
    String variableName;

    // LOCK STATEMENT CONSTRUCTOR
    public LockStmt(String variableName) {
        this.variableName = variableName;
    }

    // LOCK STATEMENT METHODS
    @Override
    public String toString() {
        return "lock("+variableName+")";
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
        if(state.getLockTable().get(foundIndex) == -1) {

            // Acquire Lock
            state.getLockTable().update(foundIndex, state.getProgramID());
        } else {

            // Wait on Lock
            state.getExecutionStack().push(new LockStmt(variableName));
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(variableName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try{
            if(!typeEnv.get(variableName).equals(new IntType()))
                throw new StmtException("LOCK STATEMENT ERROR - Variable type must be Integer");
            return typeEnv;
        } catch (Exception exc) {
            throw new StmtException("LOCK STATEMENT ERROR - " + exc);
        }
    }
}
