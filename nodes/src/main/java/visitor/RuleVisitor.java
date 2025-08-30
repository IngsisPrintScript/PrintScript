package visitor;

import expression.binary.BinaryExpression;
import responses.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public interface RuleVisitor {
    Result<String> check(LetStatementNode node);
    Result<String> check(PrintStatementNode node);
    Result<String> check(BinaryExpression node);
}
