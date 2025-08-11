package visitors;

import nodes.AbstractSyntaxTreeComponent;
import nodes.expressions.composite.AdditionExpressionNode;
import nodes.expressions.composite.AssignationExpressionNode;
import nodes.expressions.composite.MultiplicationExpressionNode;
import nodes.expressions.composite.TypeAssignationExpressionNode;
import nodes.expressions.leaf.IdentifierExpressionNode;
import nodes.expressions.leaf.LiteralExpressionNode;
import nodes.expressions.leaf.TypeExpressionNode;
import nodes.statements.LetStatementNode;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;

import java.util.List;

public record AbstractSyntaxNodeJavaTranspilationVisitor() implements AbstractSyntaxNodeVisitorInterface {
    @Override
    public Response visit(AssignationExpressionNode node) {
        return doBinaryOperation("=", "assignation", node);
    }
    @Override
    public Response visit(TypeAssignationExpressionNode node) {
        return doBinaryOperation("", "type assignation", node);}
    @Override
    public Response visit(AdditionExpressionNode node) {
        return doBinaryOperation("+", "addition", node);
    }

    @Override
    public Response visit(MultiplicationExpressionNode node) {
        return doBinaryOperation("*", "multiplication", node);
    }

    @Override
    public Response visit(LiteralExpressionNode node) {
        return new CorrectResponse<String>(node.value());
    }

    @Override
    public Response visit(TypeExpressionNode node) {
        return new CorrectResponse<String>(node.value());
    }

    @Override
    public Response visit(IdentifierExpressionNode node) {
        return new CorrectResponse<String>(node.value());
    }

    @Override
    public Response visit(LetStatementNode node) {
        Response getExpressionResponse = node.expression();
        if (!getExpressionResponse.isSuccessful()) {
            return getExpressionResponse;
        }
        Object object = ( (CorrectResponse<?>) getExpressionResponse).newObject();
        if (!(object instanceof AbstractSyntaxTreeComponent childNode)) {
            return new IncorrectResponse("The child is not an expression");
        }
        return childNode.accept(this);
    }

    private Response doBinaryOperation(String operationSymbol, String operationName, AbstractSyntaxTreeComponent node){
        Response childrenResponse = node.getChildren();
        if (!childrenResponse.isSuccessful()){
            return childrenResponse;
        }

        Object object = ( (CorrectResponse<?>) childrenResponse).newObject();
        if (!(object instanceof List<?> children)) {
            return new IncorrectResponse("The given children are not a list.");
        }

        if (children.size() != 2) {
            return new IncorrectResponse("An " + operationName + " can only have 2 children.");
        }

        Object lc = children.get(0);
        Object rc = children.get(1);
        if (!(lc instanceof AbstractSyntaxTreeComponent leftChild) || !(rc instanceof AbstractSyntaxTreeComponent rightChild)) {
            return new IncorrectResponse("The given children are not AbstractSyntaxTreeComponent.");
        }

        Response leftResponse = leftChild.accept(this);
        Response rightResponse = rightChild.accept(this);

        if (!leftResponse.isSuccessful() && !rightResponse.isSuccessful()) {
            return new IncorrectResponse("The visit to a child failed.");
        }

        Object lcr = ( (CorrectResponse<?>) leftResponse).newObject();
        Object rcr = ( (CorrectResponse<?>) rightResponse).newObject();

        if (!(lcr instanceof String leftChildResult) || !(rcr instanceof String rightChildResult)) {
            return new IncorrectResponse("The visit to a child returned something else than a string.");
        }
        if (operationSymbol.isEmpty()) {
            return new CorrectResponse<String>(leftChildResult + " " + rightChildResult);
        }
        return new CorrectResponse<String>(leftChildResult + " " + operationSymbol + " " + rightChildResult);

    }
}
