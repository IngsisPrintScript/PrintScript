package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;

public class NilEnvironment implements EnvironmentInterface {
    @Override
    public Result<String> putIdType(String id, String type) {
        return new IncorrectResult<>("Cannot put var on nil env");
    }

    @Override
    public Result<Object> putIdValue(String id, Object value) {
        return new IncorrectResult<>("Cannot put var on nil env");
    }

    @Override
    public Result<String> getIdType(String id) {
        return new IncorrectResult<>("Cannot get var on nil env");
    }

    @Override
    public Result<Object> getIdValue(String id) {
        return new IncorrectResult<>("Cannot get var on nil env");
    }

    @Override
    public Result<String> clearTypeMap() {
        return new IncorrectResult<>("Cannot empty map on nil env");
    }

    @Override
    public Result<String> clearValueMap() {
        return new IncorrectResult<>("Cannot empty map on nil env");
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return false;
    }
}
