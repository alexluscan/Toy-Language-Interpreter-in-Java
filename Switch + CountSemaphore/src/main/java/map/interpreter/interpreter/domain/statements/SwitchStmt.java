////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;
import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.expressions.RelationExp;
import map.interpreter.interpreter.domain.state.IExeStack;
import map.interpreter.interpreter.domain.types.Type;


public class SwitchStmt implements IStmt {

    Exp exp, exp1, exp2;
    IStmt stmt1, stmt2, stmt3;


    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public String toString() {
        return "switch("+ exp.toString()+") (case " + exp1.toString() +": " + stmt1.toString()+ ") (case " + exp2.toString() +": " + stmt2.toString() + ") (default: " + stmt3.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, MyDictionaryException {

        IExeStack stk = state.getExecutionStack();

        IStmt equivalentStatement = new IfStmt(new RelationExp(exp, exp1, "=="), stmt1, new IfStmt(new RelationExp(exp, exp2, "=="), stmt2, stmt3));

        stk.push(equivalentStatement);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try{

            Type type1 = exp.typeCheck(typeEnv);
            Type type2 = exp.typeCheck(typeEnv);
            Type type3 = exp.typeCheck(typeEnv);
            if(!type1.equals(type2) || !type1.equals(type3)) {
                throw new StmtException("SWITCH STATEMENT ERROR - Conditions must be of the same type");
            }

            stmt1.typeCheck(typeEnv.deepCopy());
            stmt2.typeCheck(typeEnv.deepCopy());
            stmt3.typeCheck(typeEnv.deepCopy());

            return typeEnv;

        } catch(ExpException exp) {
            throw new StmtException("SWITCH STATEMENT ERROR - " + exp);
        }
    }
}
