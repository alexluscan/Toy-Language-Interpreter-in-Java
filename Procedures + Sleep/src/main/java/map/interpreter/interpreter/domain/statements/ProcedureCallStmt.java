package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.list.MyList;
import map.interpreter.interpreter.domain.datastructures.list.MyListException;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.state.IProcTable;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.state.SymTable;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.Value;

public class ProcedureCallStmt implements IStmt {

    // PROCEDURE CALL STATEMENT STRUCTURE
    String procedureName;
    MyIList<Exp> expressions;

    // PROCEDURE CALL STATEMENT CONSTRUCTOR
    public ProcedureCallStmt(String procedureName, Exp... exps) {
        this.procedureName = procedureName;
        this.expressions = new MyList<>();
        for(Exp exp: exps)
            this.expressions.add(exp);
    }

    // PROCEDURE CALL STATEMENT METHODS
    @Override
    public String toString() {
        return "call " + procedureName + "(" + expressions.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException, MyStackException, MyListException {

        // Get Procedure Information
        IProcTable procTable = state.getProcTable();

        if(!procTable.containsKey(procedureName))
            throw new StmtException("PROCEDURE CALL STATEMENT ERROR - Procedure not defined");

        MyIList<String> formalParameters = procTable.get(procedureName).getKey();
        IStmt body = procTable.get(procedureName).getValue();

        // Evaluate Expressions
        MyIList<Value> values = new MyList<>();
        for(Exp exp: this.expressions.getContent()) {
            values.add(exp.eval(state.getSymbolsTable(), state.getHeap()));
        }

        if(values.size() != formalParameters.size())
            throw new StmtException("PROCEDURE CALL STATEMENT ERROR - Invalid number of parameters passed");

        ISymTable newSymTable = new SymTable();
        for(int i=0; i<values.size(); i++) {
            newSymTable.put(formalParameters.get(i), values.get(i));
        }

        state.getSymbolsTableStack().push(newSymTable);
        state.getExecutionStack().push(new ReturnStmt());
        state.getExecutionStack().push(body);

        // Return null
        return null;
    }

    @Override
    public IStmt deepCopy() {
        Exp[] expsArray = new Exp[expressions.size()];
        int index = 0;
        for (Exp exp : expressions.getContent())
            expsArray[index++] = exp.deepCopy();

        return new ProcedureCallStmt(procedureName, expsArray);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        return typeEnv;
    }
}
