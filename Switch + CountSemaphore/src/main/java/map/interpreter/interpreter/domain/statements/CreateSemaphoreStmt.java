package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.list.MyList;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.expressions.VarExp;
import map.interpreter.interpreter.domain.types.IntType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.IntValue;
import map.interpreter.interpreter.domain.values.Value;
import javafx.util.Pair;

public class CreateSemaphoreStmt implements IStmt{

    String variableName;
    Exp exp;

    public CreateSemaphoreStmt(String var, Exp exp) { this.variableName=var; this.exp=exp; }

    @Override
    public String toString() {
        return "createSemaphore("+ variableName + ", " + this.exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException, MyStackException {

        Value result = this.exp.eval(state.getSymbolsTable(), state.getHeap());

        if (!result.getType().equals(new IntType()))
            throw new StmtException("CREATE SEMAPHORE EXCEPTION: Expression must be of type Int");

        IntValue resultInt = (IntValue) result;

        Integer newLocation = state.getSemaphoreTable().put(new Pair<>(resultInt.getValue(), new MyList<>()));

        if (!state.getSymbolsTable().containsKey(variableName))
            throw new StmtException("CREATE SEMAPHORE EXCEPTION: Variable must be in SymbolsTable");

        Value var = new VarExp(variableName).eval(state.getSymbolsTable(), state.getHeap());

        if (!var.getType().equals(new IntType()))
            throw new StmtException("CREATE SEMAPHORE EXCEPTION: Variable must be of type Int");

        state.getSymbolsTable().update(variableName, new IntValue(newLocation));

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(this.variableName, this.exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try {
            Type typeVar = typeEnv.get(variableName);
            Type typeExp = exp.typeCheck(typeEnv);

            if(!typeVar.equals(new IntType()) || !typeExp.equals(new IntType())) {
                throw new StmtException("CREATE SEMAPHORE STATEMENT ERROR - The type of the expression / variable must be integer");
            }

            return typeEnv;

        } catch(MyDictionaryException | ExpException exp) {
            throw new StmtException("CREATE SEMAPHORE STATEMENT ERROR - " + exp);
        }
    }
}
