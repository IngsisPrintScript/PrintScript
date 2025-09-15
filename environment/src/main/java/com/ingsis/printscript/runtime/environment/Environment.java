package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Environment implements EnvironmentInterface {
    private final Map<String, String> idTypeMap;
    private final Map<String, Object> idValueMap;

    public Environment() {
        idTypeMap = new ConcurrentHashMap<>();
        idValueMap = new ConcurrentHashMap<>();
    }

    @Override
    public Result<String> putIdType(String id, String type) {
        if (variableIsDeclared(id)) {
            return new IncorrectResult<>("Id has already been declared.");
        }
        idTypeMap.put(id, type);
        return new CorrectResult<>("Id has been declared.");
    }

    @Override
    public Result<Object> putIdValue(String id, Object value) {
        if (!variableIsDeclared(id)) {
            return new IncorrectResult<>("Id has not been declared.");
        }
        idValueMap.put(id, value);
        return new CorrectResult<>(value);
    }

    @Override
    public Result<String> getIdType(String id) {
        if (!variableIsDeclared(id)) {
            return new IncorrectResult<>("Id has not been declared.");
        }
        return new CorrectResult<>(idTypeMap.get(id));
    }

    @Override
    public Result<Object> getIdValue(String id) {
        if (!variableIsDeclared(id)) {
            return new IncorrectResult<>("Id has not been declared.");
        }
        if (!idValueMap.containsKey(id)) {
            return new IncorrectResult<>("Id has not been initialized.");
        }
        return new CorrectResult<>(idValueMap.get(id));
    }

    @Override
    public Result<String> clearTypeMap() {
        idTypeMap.clear();
        return new CorrectResult<>("Type map was cleared correctly.");
    }

    @Override
    public Result<String> clearValueMap() {
        idValueMap.clear();
        return new CorrectResult<>("Value map was cleared correctly.");
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return idTypeMap.containsKey(id);
    }

    public Boolean variableIsInitialized(String id) {
        return idValueMap.containsKey(id);
    }
}
