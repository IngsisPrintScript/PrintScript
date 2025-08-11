package analyzers.semantic.SemanticVariablesTable;

import nodes.expressions.leaf.IdentifierExpressionNode;
import responses.Response;

public interface VariablesRepo {
    Response getTable();
    Response addValues(String type, String name);
    Response deleteValues(String type);

}
