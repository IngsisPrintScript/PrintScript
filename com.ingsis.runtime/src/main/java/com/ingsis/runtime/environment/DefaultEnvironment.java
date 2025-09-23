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

public record DefaultEnvironment(Map<String, VariableEntry> variables, Environment father)
        implements Environment {

    public DefaultEnvironment {
        variables = new HashMap<>(variables);
    }

    public DefaultEnvironment(Environment father) {
        this(new HashMap<>(), father);
    }

    public DefaultEnvironment() {
        this(new HashMap<>(), new GlobalEnvironment());
    }

    @Override
    public Map<String, VariableEntry> variables() {
        return new HashMap<>(variables);
    }

    @Override
    public Result<VariableEntry> putVariable(String identifier, VariableEntry variableEntry) {
        if (isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't create an already created variable.");
        } else if (!isVariableDeclaredHere(identifier)) {
            return father.putVariable(identifier, variableEntry);
        }
        variables().put(identifier, variableEntry);
        return new CorrectResult<>(variableEntry);
    }

    @Override
    public Result<VariableEntry> modifyVariable(String identifier, VariableEntry variableEntry) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't modify an uninitialized variable.");
        } else if (!isVariableDeclaredHere(identifier)) {
            return father.modifyVariable(identifier, variableEntry);
        }
        variables().put(identifier, variableEntry);
        return new CorrectResult<>(variableEntry);
    }

    @Override
    public Result<VariableEntry> getVariable(String identifier) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>(
                    "There is no variable declared with identifier: " + identifier);
        } else if (!isVariableDeclaredHere(identifier)) {
            return father().getVariable(identifier);
        }
        return new CorrectResult<>(variables().get(identifier));
    }

    @Override
    public Boolean isVariableDeclared(String identifier) {
        return isVariableDeclaredHere(identifier) || father.isVariableDeclared(identifier);
    }

    private Boolean isVariableDeclaredHere(String identifier) {
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
