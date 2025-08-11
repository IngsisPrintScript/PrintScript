package analyzers.semantic.SemanticRulesInterface;

import nodes.AbstractSyntaxTreeComponent;
import responses.Response;
import token.Token;
import token.TokenInterface;

import java.util.HashMap;
import java.util.List;

public interface SemanticRulesInterface {

    boolean match(Object childrenResponse, String operationSymbol, String operationName);
    Response getResponse(Object childrenResponse, String operationSymbol, String operationName);

}
