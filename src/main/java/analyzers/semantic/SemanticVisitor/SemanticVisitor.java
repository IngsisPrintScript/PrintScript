package analyzers.semantic.SemanticVisitor;

import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import analyzers.semantic.SemanticVariablesTable.VariablesRepo;
import nodes.AbstractSyntaxTreeComponent;
import nodes.expressions.composite.*;
import nodes.expressions.leaf.IdentifierExpressionNode;
import nodes.expressions.leaf.LiteralExpressionNode;
import nodes.expressions.leaf.TypeExpressionNode;
import nodes.statements.LetStatementNode;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import visitors.AbstractSyntaxNodeVisitorInterface;

import java.util.HashMap;
import java.util.List;

public record SemanticVisitor(SemanticRulesInterface semanticRules,
                              VariablesRepo table) implements AbstractSyntaxNodeVisitorInterface {

    @Override
    public Response visit(AssignationExpressionNode node) {
        return assignationRules("assignation", node);
    }

    @Override
    public Response visit(TypeAssignationExpressionNode node) {
        return checkRules("", "type assignation", node, null);
    }

    @Override
    public Response visit(AdditionExpressionNode node) {
        return checkRules("+", "addition", node, null);
    }

    @Override
    public Response visit(MultiplicationExpressionNode node) {
        return checkRules("*", "multiplication", node, null);
    }

    @Override
    public Response visit(LiteralExpressionNode node) {
        return new CorrectResponse<>(node.value());
    }

    @Override
    public Response visit(TypeExpressionNode node) {
        return new CorrectResponse<>(node.value());
    }

    @Override
    public Response visit(IdentifierExpressionNode node) {
        return new CorrectResponse<>(node.value());
    }

    @Override
    public Response visit(LetStatementNode node) {
        Response exprResp = node.expression();
        if (!exprResp.isSuccessful()) {
            return exprResp;
        }
        Object obj = ((CorrectResponse<?>) exprResp).newObject();
        if (!(obj instanceof AbstractSyntaxTreeComponent childNode)) {
            return new IncorrectResponse("The child is not an expression");
        }
        return childNode.accept(this);
    }

    @Override
    public Response visit(SubtractionExpressionNode node) {
        return checkRules("-", "subtraction", node, null);
    }

    @Override
    public Response visit(DivideExpressionNode node) {
        return checkRules("/", "divide", node, null);
    }

    private Response assignationRules(String operationName, AbstractSyntaxTreeComponent node) {
        Response childrenResponse = node.getChildren();
        if (!childrenResponse.isSuccessful()) {
            return childrenResponse;
        }
        Object obj = ((CorrectResponse<?>) childrenResponse).newObject();
        if (!(obj instanceof List<?> children)) {
            return new IncorrectResponse("The given children are not a list.");
        }
        if (children.size() != 2) {
            return new IncorrectResponse("An " + operationName + " can only have 2 children.");
        }
        if (children.get(1) instanceof LiteralExpressionNode literal) {
            if (children.get(0) instanceof IdentifierExpressionNode id) {
                table.addValues(id.value(), literal.value());
                return new CorrectResponse<>("Variable " + id.value() + " assigned.");
            }
        }
        AbstractSyntaxTreeComponent rightChild = (AbstractSyntaxTreeComponent) children.get(1);
        AbstractSyntaxTreeComponent leftChild = (AbstractSyntaxTreeComponent) children.get(0);
        List<String> opInfo = getSymbol(rightChild);
        if (!opInfo.isEmpty()) {
            return checkRules(opInfo.get(0), opInfo.get(1), rightChild, leftChild);
        }

        return new CorrectResponse<>("Assignation processed without additional rules.");
    }

    private Response checkRules(String operationSymbol, String operationName, AbstractSyntaxTreeComponent node, AbstractSyntaxTreeComponent leftNode) {
        Response childrenResponse = node.getChildren();
        Response response = leftNode.getChildren();
        if (!childrenResponse.isSuccessful() && !response.isSuccessful()) {
            return childrenResponse;
        }
        Object obj = ((CorrectResponse<?>) childrenResponse).newObject();
        Object leftObj = ((CorrectResponse<?>) childrenResponse).newObject();
        if (!(obj instanceof List<?> children && leftObj instanceof List<?> leftChildren)) {
            return new IncorrectResponse("The given children are not a list.");
        }

        if (semanticRules.match(children, operationSymbol, operationName)) {
            return semanticRules.getResponse(children, operationSymbol, operationName);
        }

        if (children.size() == 2 && leftChildren.get(1) instanceof IdentifierExpressionNode id) {
            Object map = (new CorrectResponse<> (table.getTable())).newObject();
            if (!(map instanceof HashMap<?,?> Table)) {
                return new IncorrectResponse("The given children are not a list.");
            }
            String value = (String) Table.get(id.value());
            //Solution Hardcode, if we have to aggregate a new type we should add the case, temporally usefully;
            if(value != null && leftChildren.getFirst() instanceof TypeExpressionNode type) {
                if(type.value().equals("String") &&
                   (((children.get(0) + operationSymbol + children.get(1)).startsWith("\"") &&
                   (children.get(0) + operationSymbol + children.get(1)).endsWith("\""))) ||
                   ((children.get(0) + operationSymbol + children.get(1)).startsWith("'") &&
                   (children.get(0) + operationSymbol + children.get(1)).endsWith("'"))) {
                   table.addValues(id.value(),(children.get(0) + operationSymbol + children.get(1)));
                    new CorrectResponse<>("Value assigned to " + id.value())
                }else if(value.matches("-?\\d+(\\.\\d+)?") && (type.value().equals("Number") &&
                        (children.get(0) + operationSymbol + children.get(1)).matches("-?\\d+(\\.\\d+)?"))){
                    table.addValues(id.value(),(children.get(0) + operationSymbol + children.get(1)));
                    new CorrectResponse<>("Value assigned to " + id.value())
                }
            }else if(value == null){
                table.addValues(id.value(), children.get(0) + operationSymbol + children.get(1));
                return new CorrectResponse<>("Value assigned to " + id.value())
            }
            return new IncorrectResponse("Cant add the value");
        }
        return new IncorrectResponse("No matching rule for " + operationName);
    }

    private List<String> getSymbol(Object node) {
        if (node instanceof AdditionExpressionNode) {
            return List.of("+", "addition");
        } else if (node instanceof MultiplicationExpressionNode) {
            return List.of("*", "multiplication");
        } else if (node instanceof DivideExpressionNode) {
            return List.of("/", "divide");
        } else if (node instanceof SubtractionExpressionNode) {
            return List.of("-", "subtraction");
        }
        return List.of();
    }
}
