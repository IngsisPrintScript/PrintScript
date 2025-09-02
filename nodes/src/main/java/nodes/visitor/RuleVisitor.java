package nodes.visitor;

import nodes.expression.binary.BinaryExpression;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import results.Result;
import nodes.statements.LetStatementNode;
import nodes.statements.PrintStatementNode;

public interface RuleVisitor {
    Result<String> check(LetStatementNode node);
    Result<String> check(PrintStatementNode node);
    Result<String> check(BinaryExpression node);
    Result<String> check(IdentifierNode node);
    Result<String> check(LiteralNode node);
}
