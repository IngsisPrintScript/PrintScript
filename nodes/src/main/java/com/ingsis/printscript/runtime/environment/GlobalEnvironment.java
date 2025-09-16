/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.runtime.functions.BuiltInFunction;
import com.ingsis.printscript.runtime.functions.PSFunction;
import com.ingsis.printscript.runtime.functions.register.BuiltInFunctionsRegister;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalEnvironment implements EnvironmentInterface {
    private final Map<String, VariableEntry> variablesMap;
    private final Map<String, BuiltInFunction> builtInFunctions;

    public GlobalEnvironment() {
        variablesMap = new ConcurrentHashMap<>();
        builtInFunctions = new BuiltInFunctionsRegister().getBuiltInFunctions();
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
        if (!variableIsDeclared(id)) {
            return new IncorrectResult<>("Variable " + id + " is not declared.");
        }
        return new CorrectResult<>(variablesMap.get(id).value());
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return variablesMap.containsKey(id);
    }

    @Override
    public Result<PSFunction> getFunction(String identifier) {
        if (!hasFunction(identifier)) {
            return new IncorrectResult<>("Function " + identifier + " is not declared.");
        }
        return new CorrectResult<>(builtInFunctions.get(identifier));
    }

    @Override
    public Boolean hasFunction(String identifier) {
        return builtInFunctions.containsKey(identifier);
    }
}
