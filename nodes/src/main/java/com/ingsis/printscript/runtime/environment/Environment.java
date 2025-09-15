/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.FunctionEntry;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.visitor.InterpretableNode;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Environment implements EnvironmentInterface {
    private final EnvironmentInterface fatherEnvironment;
    private final Map<String, VariableEntry> variablesMap;
    private final Map<String, FunctionEntry> functionsMap;

    public Environment() {
        fatherEnvironment = new NilEnvironment();
        variablesMap = new ConcurrentHashMap<>();
        functionsMap = new ConcurrentHashMap<>();
    }

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Environment(EnvironmentInterface fatherEnvironment) {
        this.fatherEnvironment = fatherEnvironment;
        variablesMap = new ConcurrentHashMap<>();
        functionsMap = new ConcurrentHashMap<>();
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
    public Result<FunctionEntry> putFunction(String identifier, FunctionEntry entry) {
        if (functionIsDeclaredHere(identifier)) {
            return new IncorrectResult<>("Function " + identifier + " is already declared.");
        }
        functionsMap.put(identifier, entry);
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
    public Result<String> getFunctionReturnType(String id) {
        if (!functionIsDeclared(id)) {
            return new IncorrectResult<>("Function " + id + " is not declared.");
        }
        return new CorrectResult<>(functionsMap.get(id).returnType());
    }

    @Override
    public Result<Collection<DeclarationArgumentNode>> getFunctionArguments(String id) {
        if (!functionIsDeclared(id)) {
            return new IncorrectResult<>("Function " + id + " is not declared.");
        }
        return new CorrectResult<>(functionsMap.get(id).arguments());
    }

    @Override
    public Result<Collection<InterpretableNode>> getFunctionBody(String id) {
        if (!functionIsDeclared(id)) {
            return new IncorrectResult<>("Function " + id + " is not declared.");
        }
        return new CorrectResult<>(functionsMap.get(id).body());
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return variableIsDeclaredHere(id) || fatherEnvironment.variableIsDeclared(id);
    }

    @Override
    public Boolean functionIsDeclared(String id) {
        return functionIsDeclaredHere(id) || fatherEnvironment.functionIsDeclared(id);
    }

    private Boolean variableIsDeclaredHere(String id) {
        return variablesMap.containsKey(id);
    }

    private Boolean functionIsDeclaredHere(String id) {
        return variablesMap.containsKey(id);
    }
}
