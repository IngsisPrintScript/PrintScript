package common.VariablesTable;

import common.responses.Result;

public interface VariablesTableInterface {

    Result getTable();
    Result getValue(String name);
    Result addValue(String name,String value);
    Result removeValue(String name);

}
