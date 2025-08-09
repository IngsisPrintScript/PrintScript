package visitors;

import nodes.expressions.composite.AdditionExpressionNode;
import nodes.expressions.composite.AssignationExpressionNode;
import nodes.expressions.composite.TypeAssignationExpressionNode;
import nodes.expressions.leaf.IdentifierExpressionNode;
import nodes.expressions.leaf.LiteralExpressionNode;
import nodes.expressions.composite.MultiplicationExpressionNode;
import nodes.expressions.leaf.TypeExpressionNode;
import nodes.statements.LetStatementNode;
import responses.Response;

public interface AbstractSyntaxNodeVisitorInterface {
    Response visit(AssignationExpressionNode node);
    Response visit(TypeAssignationExpressionNode node);
    Response visit(AdditionExpressionNode node);
    Response visit(MultiplicationExpressionNode node);
    Response visit(LiteralExpressionNode node);
    Response visit(TypeExpressionNode node);
    Response visit(IdentifierExpressionNode node);
    Response visit(LetStatementNode node);
}
