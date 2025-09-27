/*
 * My Project
 */

package com.ingsis.runtime.environment;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.environment.entries.VariableEntry;
import java.util.HashMap;
import java.util.Map;

public final class GlobalEnvironment implements Environment {
    private final Map<String, VariableEntry> variables;
    private Result<String> executionResult;

    public GlobalEnvironment(Map<String, VariableEntry> variables) {
        this.variables = new HashMap<>(variables);
    }

    public GlobalEnvironment() {
        this(new HashMap<>());
    }

    private Map<String, VariableEntry> variables() {
        return new HashMap<>(variables);
    }

    @Override
    public Result<VariableEntry> putVariable(String identifier, VariableEntry variableEntry) {
        if (isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't create an already created variable.");
        }
        variables().put(identifier, variableEntry);
        return new CorrectResult<>(variableEntry);
    }

    @Override
    public Result<VariableEntry> modifyVariable(String identifier, VariableEntry variableEntry) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't modify an uninitialized variable.");
        }
        variables().put(identifier, variableEntry);
        return new CorrectResult<>(variableEntry);
    }

    @Override
    public Result<VariableEntry> getVariable(String identifier) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>(
                    "There is no variable declared with identifier: " + identifier);
        }
        return new CorrectResult<>(variables().get(identifier));
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
