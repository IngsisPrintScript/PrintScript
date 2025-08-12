package parser.SemanticRules.Types;

import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import common.nodes.expressions.leaf.LiteralExpressionNode;
import common.nodes.expressions.leaf.TypeExpressionNode;
import common.responses.IncorrectResponse;
import common.responses.Response;

import java.util.List;

public record NumberTypeMatchRule(SemanticRulesInterface nextSemanticRules) implements SemanticRulesInterface {
    @Override
    public boolean match(Object childrenResponse, String operationSymbol, String operationName) {
        List<?> children = (List<?>) childrenResponse;
        if(children.size() == 1){
            return false;
        }
        TypeExpressionNode typeExpressionNode = (TypeExpressionNode) children.get(0);
        LiteralExpressionNode literalNode = (LiteralExpressionNode) children.get(1);
        if(!typeExpressionNode.value().equals("Number")){
            return false;
        }
        String literalValue = literalNode.value().trim();
        return !literalValue.matches("-?\\d+(\\.\\d+)?");
    }

    @Override
    public Response getResponse(Object childrenResponse, String operationSymbol, String operationName) {
        if(match(childrenResponse, operationSymbol, operationName)){
            return new IncorrectResponse("Type mismatch");
        }
        return nextSemanticRules.getResponse(childrenResponse, operationSymbol, operationName);
    }
}
