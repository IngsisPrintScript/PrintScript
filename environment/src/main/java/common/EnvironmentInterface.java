package common;

import responses.Result;

public interface EnvironmentInterface {
    Result putIdType(String id, String type);
    Record putIdValue(String id, Object value);
    Result getIdType(String id);
    Result getIdValue(String id);
    Boolean variableIsDeclared(String id);
}
