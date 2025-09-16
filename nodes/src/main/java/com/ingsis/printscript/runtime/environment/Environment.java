/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.runtime.functions.PSFunction;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Environment implements EnvironmentInterface {
    private final EnvironmentInterface fatherEnvironment;
    private final Map<String, VariableEntry> variablesMap;

    public Environment() {
        fatherEnvironment = new NilEnvironment();
        variablesMap = new ConcurrentHashMap<>();
    }

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Environment(EnvironmentInterface fatherEnvironment) {
        this.fatherEnvironment = fatherEnvironment;
        variablesMap = new ConcurrentHashMap<>();
    }

    @Override
    public Result<VariableEntry> putVariable(String identifier, VariableEntry entry) {
        if (variableIsDeclared(identifier)) {
            return new IncorrectResult<>("Variable " + identifier + " is already declared.");
        }
        variablesMap.put(identifier, entry);
        return new CorrectResult<>(entry);
    }

    @Override
    public Result<VariableEntry> modifyVariableValue(String identifier, Object value) {
        if (!variableIsDeclared(identifier)) {
            return new IncorrectResult<>("Variable " + identifier + " is not declared.");
        }
        VariableEntry entry = variablesMap.get(identifier);
        VariableEntry newEntry = new VariableEntry(entry.type(), value);
        variablesMap.put(identifier, newEntry);
        return new CorrectResult<>(newEntry);
    }

    @Override
    public Result<String> getVariableType(String id) {
        if (!variableIsDeclaredHere(id)) {
            return fatherEnvironment.getVariableType(id);
        }
        return new CorrectResult<>(variablesMap.get(id).type());
    }

    @Override
    public Result<Object> getVariableValue(String id) {
        if (!variableIsDeclaredHere(id)) {
            return fatherEnvironment.getVariableValue(id);
        }
        return new CorrectResult<>(variablesMap.get(id).value());
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return variableIsDeclaredHere(id) || fatherEnvironment.variableIsDeclared(id);
    }

    @Override
    public Result<PSFunction> getFunction(String identifier) {
        return fatherEnvironment.getFunction(identifier);
    }

    @Override
    public Boolean hasFunction(String identifier) {
        return fatherEnvironment.hasFunction(identifier);
    }

    private Boolean variableIsDeclaredHere(String id) {
        return variablesMap.containsKey(id);
    }
}
