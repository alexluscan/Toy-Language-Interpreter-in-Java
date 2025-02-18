package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.state.ISemaphoreTable;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.IntType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.IntValue;
import map.interpreter.interpreter.domain.values.Value;
import javafx.util.Pair;

public class AcquireSemaphoreStmt implements IStmt {

    String variableName;

    public AcquireSemaphoreStmt(String variableName) { this.variableName = variableName; }

    @Override
    public String toString() {
        return "acquireSemaphore("+ variableName + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException, MyStackException {

        ISymTable symTable = state.getSymbolsTable();
        ISemaphoreTable semaphoreTable = state.getSemaphoreTable();

        if(!symTable.containsKey(variableName))
            throw new StmtException("ACQUIRE SEMAPHORE STATEMENT ERROR - Variable not inside symbols table");

        Value result = symTable.get(variableName);
        Type resultType = result.getType();

        if(!resultType.equals(new IntType()))
            throw new StmtException("ACQUIRE SEMAPHORE STATEMENT ERROR - Variable must be of type integer");

        IntValue resultInt = (IntValue) result;
        Integer foundIndex = resultInt.getValue();

        if(!semaphoreTable.containsKey(foundIndex))
            throw new StmtException("ACQUIRE SEMAPHORE STATEMENT ERROR - Found Index is not inside semaphore table");

        Pair<Integer, MyIList<Integer>> pair = semaphoreTable.get(foundIndex);
        Integer n1 = pair.getKey();
        Integer nl = pair.getValue().size();

        if(n1>nl) {
            if (!pair.getValue().getContent().contains(state.getProgramID()))
                pair.getValue().add(state.getProgramID());
        } else {
            state.getExecutionStack().push(new AcquireSemaphoreStmt(variableName));
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireSemaphoreStmt(this.variableName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try {
            Type typeVar = typeEnv.get(variableName);

            if(!typeVar.equals(new IntType())) {
                throw new StmtException("ACQUIRE SEMAPHORE STATEMENT ERROR - The type of the variable must be integer");
            }

            return typeEnv;

        } catch(MyDictionaryException exp) {
            throw new StmtException("ACQUIRE SEMAPHORE STATEMENT ERROR - " + exp);
        }
    }
}
