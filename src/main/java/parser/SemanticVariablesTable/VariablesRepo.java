package parser.SemanticVariablesTable;

import common.responses.Response;

public interface VariablesRepo {
    Response getTable();
    Response addValues(String type, String name);
    Response deleteValues(String type);

}
