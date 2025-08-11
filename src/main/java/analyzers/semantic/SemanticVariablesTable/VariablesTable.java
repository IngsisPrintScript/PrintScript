package analyzers.semantic.SemanticVariablesTable;

import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;

import java.util.HashMap;

public record VariablesTable(HashMap<String,String> table) implements VariablesRepo {
    @Override
    public Response getTable() {
        if(table.isEmpty()){
            return new IncorrectResponse("Table is empty");
        }
        return new CorrectResponse<>(table);
    }

    @Override
    public Response addValues(String type, String name) {
        if (table.containsKey(type)) {
            return new IncorrectResponse("Type already exists");
        }
        return new CorrectResponse<>(table.put(type,name));
    }

    @Override
    public Response deleteValues(String type) {
        if(!table.containsKey(type)){
            return new IncorrectResponse("Type doesn't exists");
        }
        return new CorrectResponse<>(table.remove(type));
    }
}
