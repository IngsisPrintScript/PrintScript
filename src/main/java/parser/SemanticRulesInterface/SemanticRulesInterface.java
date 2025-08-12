package parser.SemanticRulesInterface;

import common.responses.Response;

public interface SemanticRulesInterface {

    boolean match(Object childrenResponse, String operationSymbol, String operationName);
    Response getResponse(Object childrenResponse, String operationSymbol, String operationName);

}
