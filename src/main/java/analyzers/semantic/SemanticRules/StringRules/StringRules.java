package analyzers.semantic.SemanticRules.StringRules;

import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import nodes.expressions.leaf.LiteralExpressionNode;
import nodes.expressions.leaf.TypeExpressionNode;
import responses.IncorrectResponse;
import responses.Response;

import java.util.List;

public record StringRules(SemanticRulesInterface nextSemanticRules) implements SemanticRulesInterface {
    @Override
    public boolean match(Object childrenResponse, String operationSymbol, String operationName){
        List<?> children = (List<?>) childrenResponse;
        TypeExpressionNode typeExpressionNode = (TypeExpressionNode) children.get(0);
        if(children.size() == 1){
            return false;
        }
        LiteralExpressionNode literalNode = (LiteralExpressionNode) children.get(1);
        if(!typeExpressionNode.value().equals("String")){
            return false;
        }
        String trimmed = literalNode.value();
        return !(trimmed.matches("([\"'])[^\"]*\\1") || trimmed.matches("([\"'])[^\']*\\1"));
    }

    @Override
    public Response getResponse(Object childrenResponse, String operationSymbol, String operationName) {
        if(match(childrenResponse, operationSymbol, operationName)){
            return new IncorrectResponse("malformed string literal");
        }
        return nextSemanticRules.getResponse(childrenResponse, operationSymbol, operationName);
    }
}
