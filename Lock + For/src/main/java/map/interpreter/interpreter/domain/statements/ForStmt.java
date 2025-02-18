package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.expressions.RelationExp;
import map.interpreter.interpreter.domain.expressions.VarExp;
import map.interpreter.interpreter.domain.types.IntType;
import map.interpreter.interpreter.domain.types.Type;

public class ForStmt implements IStmt {

    // FOR STATEMENT STRUCTURE
    String variableName;
    Exp exp1, exp2, exp3;
    IStmt stmt;

    // FOR STATEMENT CONSTRUCTOR
    public ForStmt(String variableName, Exp exp1, Exp exp2, Exp exp3, IStmt stmt) {
        this.variableName = variableName;
        this.exp1 = exp1;
        this.exp2= exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    // FOR STATEMENT METHODS
    @Override
    public String toString() {
        return "for("+ variableName +"="+exp1.toString()+"; " + variableName + "<" + exp2.toString() + "; " + variableName +"="+exp3.toString()+") { " + stmt.toString() + " }";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException {

        IStmt equivalentStmt = new CompStmt(
                new VarDeclStmt(variableName, new IntType()),
                new CompStmt(
                        new AssignStmt(variableName, exp1),
                        new WhileStmt(
                                new RelationExp(new VarExp(variableName), exp2, "<"),
                                new CompStmt(
                                        stmt,
                                        new AssignStmt(variableName, exp3)
                                )
                        )
                )
        );

        state.getExecutionStack().push(equivalentStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(variableName, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try {
            typeEnv.put(variableName, new IntType());
            Type exp1Type = exp1.typeCheck(typeEnv);
            Type exp2Type = exp2.typeCheck(typeEnv);
            Type exp3Type = exp3.typeCheck(typeEnv);

            if(!exp1Type.equals(new IntType()) || !exp2Type.equals(new IntType()) || !exp3Type.equals(new IntType()))
                throw new StmtException("FOR STATEMENT ERROR - Expressions must be of type Int");

            return stmt.typeCheck(typeEnv);
        } catch (Exception exc) {
            throw new StmtException("FOR STATEMENT ERROR - " + exc);
        }
    }
}
