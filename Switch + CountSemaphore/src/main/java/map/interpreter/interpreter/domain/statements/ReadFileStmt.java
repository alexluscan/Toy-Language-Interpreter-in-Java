////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.statements;


import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.expressions.Exp;
import map.interpreter.interpreter.domain.expressions.ExpException;
import map.interpreter.interpreter.domain.state.IFileTable;
import map.interpreter.interpreter.domain.state.ISymTable;
import map.interpreter.interpreter.domain.types.IntType;
import map.interpreter.interpreter.domain.types.StringType;
import map.interpreter.interpreter.domain.types.Type;
import map.interpreter.interpreter.domain.values.IntValue;
import map.interpreter.interpreter.domain.values.StringValue;
import map.interpreter.interpreter.domain.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class ReadFileStmt implements IStmt {

    // READ FILE STATEMENT STRUCTURE
    // A read file statement is formed of a string type expression and a variable's name to read into
    Exp expression;
    String variableName;

    // READ FILE STATEMENT CONSTRUCTORS
    public ReadFileStmt(Exp exp, String varName) {
        this.expression = exp;
        this.variableName = varName;
    }

    // READ FILE STATEMENT METHODS
    // To String Method
    @Override
    public String toString() {
        return "readFile(" + expression.toString() + "," + variableName +")";
    }

    // Executes the statement of the program defined by Program State
    @Override
    public PrgState execute(PrgState state) throws StmtException, MyDictionaryException, ExpException {

        // Get the current stack, symTable and file table
        ISymTable symTbl = state.getSymbolsTable();
        IFileTable fileTable = state.getFileTable();

        // Check if variable exists inside the symTable
        if(!symTbl.containsKey(variableName))
            throw new StmtException("Variable does not exist in the symbolic table");

        // Check the type of variable
        // Must be IntType
        Value v = symTbl.get(variableName);
        if(!v.getType().equals(new IntType()))
            throw new StmtException("Variable must be of type Integer");

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
        if(!fileTable.containsKey(s))
            throw new StmtException("File is not open inside the file table");

        // Read from file with Buffered Reader into variable
        try {

            // Read a line from the file
            BufferedReader br = fileTable.get(s);
            String line = br.readLine();

            // Get the contents of the new line
            // Empty line
            IntValue readValue;
            if(line == null)
                readValue = new IntValue(0);

            // Integer Value
            else
                readValue = new IntValue(Integer.parseInt(line));

            // Update the value inside the symbolic table
            symTbl.update(variableName, readValue);

        }catch (IOException e) {
            throw new StmtException("There was an error opening the file: " + e);
        }

        // Return null
        return null;
    }

    // Returns a copy of the type
    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(this.expression.deepCopy(), this.variableName);
    }

    // Typechecking mechanism
    // Ensure statement can be run
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtException {
        try {
            Type typeExp = expression.typeCheck(typeEnv);
            Type typeVar = typeEnv.get(variableName);

            if(!typeExp.equals(new StringType())){
                throw new StmtException("READ FILE STATEMENT ERROR - Filename must be of type String");
            }

            if(!typeVar.equals(new IntType())){
                throw new StmtException("READ FILE STATEMENT ERROR - Variable must be of type Integer");
            }

            return typeEnv;

        } catch (MyDictionaryException | ExpException exp) {
            throw new StmtException("READ FILE STATEMENT ERROR - " + exp);
        }
    }
}
