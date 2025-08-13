package common.VariablesTable;

import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

import java.util.HashMap;

public record VariablesTable(HashMap<String,String> table) implements VariablesTableInterface{
    @Override
    public Result getTable() {
        if(table==null || table.isEmpty()){
            return new IncorrectResult("Table is empty");
        }
        return new CorrectResult<>(this.table);
    }

    @Override
    public Result getValue(String name) {
        if(table.get(name)==null){
            return new IncorrectResult("Value not found");
        }
        return new CorrectResult<>(this.table.get(name));
    }

    @Override
    public Result addValue(String name, String value) {
        return new CorrectResult<>(this.table.put(name,value));
    }

    @Override
    public Result removeValue(String name) {
        if(table.get(name)==null){
            return new IncorrectResult("Value not found");
        }
        return new CorrectResult<>(this.table.remove(name));
    }
}
