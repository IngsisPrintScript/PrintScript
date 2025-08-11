package analyzers.semantic.SemanticRules.Subtract;

import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import responses.IncorrectResponse;
import responses.Response;

import java.util.List;

public record SubtractStringsRule(SemanticRulesInterface nextSemanticRules) implements SemanticRulesInterface {

    @Override
    public boolean match(Object childrenResponse, String operationSymbol, String operationName) {
        if(!operationSymbol.equals("-")){
            return true;
        }
        String left = ((List<String>) childrenResponse).get(0).trim();
        String right = ((List<String>) childrenResponse).get(1).trim();
        return  left.matches("-?\\d+(\\.\\d+)?") && right.matches("-?\\d+(\\.\\d+)?");
    }

    @Override
    public Response getResponse(Object childrenResponse, String operationSymbol, String operationName) {
        if(match(childrenResponse, operationSymbol, operationName)){
            return  new IncorrectResponse("Cant subtract two Strings.");
        }
        return nextSemanticRules().getResponse(childrenResponse, operationSymbol, operationName);
    }
}
