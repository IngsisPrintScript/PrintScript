package interpreter.visitors;

import common.nodes.expressions.composite.*;
import common.nodes.expressions.leaf.IdentifierExpressionNode;
import common.nodes.expressions.leaf.LiteralExpressionNode;
import common.nodes.expressions.leaf.TypeExpressionNode;
import common.nodes.statements.LetStatementNode;
import common.responses.Response;

public interface AbstractSyntaxNodeVisitorInterface {
    Response visit(AssignationExpressionNode node);
    Response visit(TypeAssignationExpressionNode node);
    Response visit(AdditionExpressionNode node);
    Response visit(MultiplicationExpressionNode node);
    Response visit(LiteralExpressionNode node);
    Response visit(TypeExpressionNode node);
    Response visit(IdentifierExpressionNode node);
    Response visit(LetStatementNode node);
    Response visit(SubtractionExpressionNode subtractionExpressionNode);
    Response visit(DivideExpressionNode divideExpressionNode);
}
