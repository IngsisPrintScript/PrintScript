package visitor;

import expression.binary.BinaryExpression;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import results.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public interface RuleVisitor {
    Result<String> check(LetStatementNode node);
    Result<String> check(PrintStatementNode node);
    Result<String> check(BinaryExpression node);
    Result<String> check(IdentifierNode node);
    Result<String> check(LiteralNode node);
}
