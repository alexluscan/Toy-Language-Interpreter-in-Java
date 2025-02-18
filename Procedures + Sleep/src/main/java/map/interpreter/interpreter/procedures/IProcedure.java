package map.interpreter.interpreter.procedures;

import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.statements.IStmt;

public interface IProcedure {
    String getName();
    MyIList<String> getParameters();
    IStmt getStmt();

}
