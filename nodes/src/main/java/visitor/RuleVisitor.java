package visitor;

import expression.binary.BinaryExpression;
import responses.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public interface RuleVisitor {
    Result check(LetStatementNode node);
    Result check(PrintStatementNode node);
    Result check(BinaryExpression node);
}
