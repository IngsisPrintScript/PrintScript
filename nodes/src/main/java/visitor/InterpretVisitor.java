package visitor;

import expression.ExpressionNode;
import results.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public interface InterpretVisitor {
    Result<String> interpret(LetStatementNode statement);
    Result<String> interpret(PrintStatementNode statement);
    Result<String> interpret(ExpressionNode expression);
}
