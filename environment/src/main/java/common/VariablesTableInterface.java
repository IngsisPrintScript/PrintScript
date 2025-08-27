package common;

import common.Symbol.Symbol;
import responses.Result;

public interface VariablesTableInterface {

    Result getTable();
    Result getValue(String name);
    Result addValue(String name, Symbol value);
    Result removeValue(String name);
    Result exist(String name);

}
