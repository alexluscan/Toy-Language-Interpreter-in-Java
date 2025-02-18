////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.state.IFileTable;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.StringType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.StringValue;
import map.interpreter.interpreter.domain.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class OpenRFileStmt implements IStmt {

    // OPEN READ FILE STATEMENT STRUCTURE
    // An open read file statement is formed of a string type expression
    Exp expression;

    // OPEN READ FILE STATEMENT CONSTRUCTORS
    public OpenRFileStmt(Exp exp) { this.expression = exp; }

    // OPEN READ FILE STATEMENT METHODS
    // To String Method
    @Override
    public String toString() {
        return "openRFile(" + expression.toString()+")";
    }

    // Executes the statement of the program defined by Program State
    @Override
    public PrgState execute(PrgState state) throws StmtException, MyDictionaryException, ExpException {

        // Get the current stack, symTable and file table
        ISymTable symTbl = null;
        try {
            symTbl = state.getSymbolsTable();
        } catch (MyStackException e) {
            throw new RuntimeException(e);
        }
        IFileTable fileTable = state.getFileTable();

        // Evaluate the expression of the RFile
        Value val = expression.eval(symTbl, state.getHeap());

        // Check type of the value
        // Must be string
        if(!val.getType().equals(new StringType())) {
            throw new StmtException("Filename must be of type String");
        }

        // Safely cast to StringValue
        StringValue s = (StringValue) val;

        // Check if file is not already in the file table
        if(fileTable.containsKey(s))
            throw new StmtException("File is already open inside the file table");

        // Open file with Buffered Reader
        try {
            BufferedReader br = new BufferedReader(new FileReader(s.getValue()));

            // Add entrance in the file table
            fileTable.put(s, br);

        }catch (IOException e) {
            throw new StmtException("There was an error opening the file: " + e);
        }

        // Return null
        return null;
    }

    // Returns a copy of the type
    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(this.expression.deepCopy());
    }

    // Typechecking mechanism
    // Ensure statement can be run
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {

        try {

            // Check expression type - must be string
            Type type = expression.typeCheck(typeEnv);
            if(!type.equals(new StringType())){
                throw new StmtException("CLOSE READ FILE STATEMENT ERROR - Type of the expression must be StringType");
            }

            // Return the typechecking dictionary
            return typeEnv;
        } catch (ExpException exp) {
            throw new StmtException("CLOSE READ FILE STATEMENT ERROR - " + exp);
        }
    }
}
