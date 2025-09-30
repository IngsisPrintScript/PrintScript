/*
 * My Project
 */

package com.ingsis.runtime.environment;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.runtime.environment.entries.factories.EntryFactory;
import com.ingsis.types.Types;
import java.util.HashMap;
import java.util.Map;

public final class GlobalEnvironment implements Environment {
    private final EntryFactory entryFactory;
    private final Map<String, VariableEntry> variables;
    private Result<String> executionResult;

    public GlobalEnvironment(EntryFactory entryFactory, Map<String, VariableEntry> variables) {
        this.entryFactory = entryFactory;
        this.variables = new HashMap<>(variables);
    }

    public GlobalEnvironment(EntryFactory entryFactory) {
        this(entryFactory, new HashMap<>());
    }

    @Override
    public Result<VariableEntry> putVariable(String identifier, Types type) {
        if (isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't create an already created variable.");
        }
        VariableEntry variableEntry = entryFactory.createVariableEntry(type, null);
        return new CorrectResult<>(variables.put(identifier, variableEntry));
    }

    @Override
    public Result<VariableEntry> modifyVariable(String identifier, Object value) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't modify an uninitialized variable.");
        }
        VariableEntry oldVariableEntry = variables.get(identifier);
        Types type = oldVariableEntry.type();
        VariableEntry newVariableEntry = entryFactory.createVariableEntry(type, value);
        return new CorrectResult<>(variables.put(identifier, newVariableEntry));
    }

    @Override
    public Result<VariableEntry> getVariable(String identifier) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>(
                    "There is no variable declared with identifier: " + identifier);
        }
        return new CorrectResult<>(variables.get(identifier));
    }

    @Override
    public void setExecutionResult(Result<String> result) {
        this.executionResult = result;
    }

    @Override
    public Result<String> getExecutionResult() {
        return this.executionResult;
    }

    @Override
    public Boolean isVariableDeclared(String identifier) {
        return variables.containsKey(identifier);
    }

    @Override
    public Boolean isVariableInitialized(String identifier) {
        Result<VariableEntry> getVariableResult = getVariable(identifier);
        if (!getVariableResult.isCorrect()) {
            return false;
        }
        Object value = getVariableResult.result().value();
        return value != null;
    }
}
