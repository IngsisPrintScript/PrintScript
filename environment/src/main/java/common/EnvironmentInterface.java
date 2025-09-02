package common;

import results.Result;

public interface EnvironmentInterface {
    Result<String> putIdType(String id, String type);
    Result<Object> putIdValue(String id, Object value);
    Result<String> getIdType(String id);
    Result<Object> getIdValue(String id);
    Result<String> clearTypeMap();
    Result<String> clearValueMap();
    Boolean variableIsDeclared(String id);
}
