/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalEnvironment implements EnvironmentInterface {
    private final Map<String, VariableEntry> variablesMap;

    public GlobalEnvironment() {
        variablesMap = new ConcurrentHashMap<>();
    }

    @Override
    public Result<VariableEntry> putVariable(String identifier, VariableEntry entry) {
        if (variableIsDeclared(identifier)) {
            return new IncorrectResult<>("Variable " + identifier + " is already declared.");
        }
        return new CorrectResult<>(variablesMap.put(identifier, entry));
    }

    @Override
    public Result<VariableEntry> modifyVariableValue(String identifier, Object value) {
        if (!variableIsDeclared(identifier)) {
            return new IncorrectResult<>("Variable " + identifier + " is not declared.");
        }
        VariableEntry variableEntry = variablesMap.get(identifier);
        VariableEntry newVariableEntry = new VariableEntry(variableEntry.type(), value);
        variablesMap.put(identifier, newVariableEntry);
        return new CorrectResult<>(newVariableEntry);
    }

    @Override
    public Result<String> getVariableType(String id) {
        if (!variableIsDeclared(id)) {
            return new IncorrectResult<>("Variable " + id + " is not declared.");
        }
        return new CorrectResult<>(variablesMap.get(id).type());
    }

    @Override
    public Result<Object> getVariableValue(String id) {
        if (variableIsDeclared(id)) {
            return new IncorrectResult<>("Variable " + id + " is not declared.");
        }
        return new CorrectResult<>(variablesMap.get(id).value());
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return variablesMap.containsKey(id);
    }
}
