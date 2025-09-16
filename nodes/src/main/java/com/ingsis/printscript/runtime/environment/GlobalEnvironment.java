package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.FunctionEntry;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.runtime.functions.BuiltInFunction;
import com.ingsis.printscript.runtime.functions.register.BuiltInFunctionsRegister;
import com.ingsis.printscript.visitor.InterpretableNode;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalEnvironment implements EnvironmentInterface {
    private final Map<String, VariableEntry> variablesMap;
    private final Map<String, FunctionEntry> functionsMap;
    private final Map<String, BuiltInFunction> systemFunctionsMap;

    public GlobalEnvironment() {
        variablesMap = new ConcurrentHashMap<>();
        functionsMap = new ConcurrentHashMap<>();
        systemFunctionsMap = new BuiltInFunctionsRegister().getBuiltInFunctions();
    }

    @Override
    public Result<VariableEntry> putVariable(String identifier, VariableEntry entry) {
        if (variableIsDeclared(identifier)) {
            return new IncorrectResult<>("Variable " + identifier + " is already declared.");
        }
        return new CorrectResult<>(variablesMap.put(identifier, entry));
    }

    @Override
    public Result<FunctionEntry> putFunction(String identifier, FunctionEntry entry) {
        if (functionIsDeclareAndNotBuiltIn(identifier)) {
            return new IncorrectResult<>("Function " + identifier + " is already declared.");
        }
        return new CorrectResult<>(functionsMap.put(identifier, entry));
    }

    @Override
    public Result<VariableEntry> modifyVariableValue(String identifier, Object value) {
        if(!variableIsDeclared(identifier)){
            return new IncorrectResult<>("Variable " + identifier + " is not declared.");
        }
        VariableEntry variableEntry = variablesMap.get(identifier);
        VariableEntry newVariableEntry = new VariableEntry(variableEntry.type(), value);
        variablesMap.put(identifier, newVariableEntry);
        return new CorrectResult<>(newVariableEntry);
    }

    @Override
    public Result<String> getVariableType(String id) {
        if (!variableIsDeclared(id)){
            return new IncorrectResult<>("Variable " + id + " is not declared.");
        }
        return new CorrectResult<>(variablesMap.get(id).type());
    }

    @Override
    public Result<Object> getVariableValue(String id) {
        if (variableIsDeclared(id)){
            return new IncorrectResult<>("Variable " + id + " is not declared.");
        }
        return new CorrectResult<>(variablesMap.get(id).value());
    }

    @Override
    public Result<String> getFunctionReturnType(String id) {
        if (!functionIsDeclared(id)) {
            return new IncorrectResult<>("Function " + id + " is not declared");
        }
        return new CorrectResult<>(functionsMap.get(id).returnType());
    }

    @Override
    public Result<Collection<DeclarationArgumentNode>> getFunctionArguments(String id) {
        if(!functionIsDeclared(id)){
            return new IncorrectResult<>("Function " + id + " is not declared.");
        }
        return new CorrectResult<>(functionsMap.get(id).arguments());
    }

    @Override
    public Result<Collection<InterpretableNode>> getFunctionBody(String id) {
        if (!functionIsDeclared(id)){
            return new IncorrectResult<>("Function " + id + " is not declared.");
        }
        return new CorrectResult<>(functionsMap.get(id).body());
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return variablesMap.containsKey(id);
    }

    @Override
    public Boolean functionIsDeclared(String id) {
        return functionIsDeclareAndNotBuiltIn(id) || systemFunctionsMap.containsKey(id);
    }

    private Boolean functionIsDeclareAndNotBuiltIn(String id) {
        return functionsMap.containsKey(id);
    }
}
