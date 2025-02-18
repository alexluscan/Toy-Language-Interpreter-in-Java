package map.interpreter.interpreter.procedures;

import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.list.MyList;
import map.interpreter.interpreter.domain.statements.IStmt;

public class Procedure implements IProcedure {

    // PROCEDURE STRUCTURE
    String procedureName;
    MyIList<String> parameters;
    IStmt stmt;

    // PROCEDURE CONSTRUCTOR
    public Procedure(String procedureName, IStmt stmt, String... params ) {
        this.procedureName = procedureName;
        this.stmt = stmt;

        this.parameters = new MyList<>();
        for(String parameter:params)
            this.parameters.add(parameter);
    }

    // PROCEDURE METHODS
    @Override
    public String toString() {
        return "procedure " + procedureName + "(" + parameters.toString() + ")";
    }


    @Override
    public String getName() {
        return procedureName;
    }

    @Override
    public MyIList<String> getParameters() {
        return parameters;
    }

    @Override
    public IStmt getStmt() {
        return stmt;
    }
}
