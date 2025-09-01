package common;

import responses.Result;

public interface EnvironmentInterface {
    Result<String> putIdType(String id, String type);
    Result<Object> putIdValue(String id, Object value);
    Result<String> getIdType(String id);
    Result<Object> getIdValue(String id);
    Boolean variableIsDeclared(String id);
}
