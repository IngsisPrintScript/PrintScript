package common.visitor;

import common.nodes.expression.Declaration.DeclarationNode;
import common.nodes.expression.Identifier.IdentifierNode;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.binary.BinaryExpression;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;
import common.nodes.statements.PrintStatementNode;
import common.responses.Result;

public interface VisitorInterface {
    Result visit(LetStatementNode node);
    Result visit(PrintStatementNode node);
    Result visit(AdditionNode node);
    Result visit(LiteralNode node);
    Result visit(IdentifierNode node);
    Result visit(DeclarationNode node);
    Result visit(BinaryExpression node);
}
