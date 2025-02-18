package map.interpreter.interpreter.domain.utils;

import map.interpreter.interpreter.domain.statements.CompStmt;
import map.interpreter.interpreter.domain.statements.IStmt;

import java.util.ArrayList;
import java.util.List;

public class StmtParsing {

    public static void infixTraversal(IStmt stmt, List<IStmt> result) {
        if (stmt instanceof CompStmt compStmt) {

            // Traverse left child
            infixTraversal(compStmt.firstIStmt, result);

            // Visit root
            // result.add(stmt);

            // Traverse right child
            infixTraversal(compStmt.secondIStmt, result);
        } else if (stmt != null) {

            // If it's not a CompStmt, add it as a leaf node
            result.add(stmt);
        }
    }

    public static String toFString(IStmt stmt) {
        List<String> result = new ArrayList<>();
        List<IStmt> traversalResult = new ArrayList<>();

        // Perform the infix traversal
        infixTraversal(stmt, traversalResult);
        for (IStmt s : traversalResult) {
            result.add(s.toString());
        }

        // Join all parts with a newline
        return String.join(";\n", result) + ";";
    }
}
