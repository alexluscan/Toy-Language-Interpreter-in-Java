package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.types.Type;

public class SleepStmt implements IStmt {

    // SLEEP STATEMENT STRUCTURE
    Integer number;

    // SLEEP STATEMENT CONSTRUCTOR
    public SleepStmt(Integer number) {
        this.number = number;
    }

    // SLEEP STATEMENT METHODS
    @Override
    public String toString() {
        return "sleep("+number+")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException {
        if(number > 0)
            state.getExecutionStack().push(new SleepStmt(number-1));

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        return typeEnv;
    }
}
