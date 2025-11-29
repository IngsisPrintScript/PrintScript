/*
 * My Project
 */

package com.ingsis.runtime.environment;

import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.runtime.environment.entries.factories.EntryFactory;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GlobalEnvironment implements Environment {
    private final EntryFactory entryFactory;
    private final Map<String, FunctionEntry> functions;
    private final Map<String, VariableEntry> variables;

    public GlobalEnvironment(
            EntryFactory entryFactory,
            Map<String, VariableEntry> variables,
            Map<String, FunctionEntry> functions) {
        this.entryFactory = entryFactory;
        this.variables = new HashMap<>(variables);
        this.functions = new HashMap<>(functions);
    }

    public GlobalEnvironment(EntryFactory entryFactory) {
        this(entryFactory, new HashMap<>(), new HashMap<>());
    }

    @Override
    public Result<VariableEntry> createVariable(String identifier, Types type) {
        if (isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't create an already created variable.");
        }
        VariableEntry variableEntry = entryFactory.createVariableEntry(type, null, true);
        return new CorrectResult<>(variables.put(identifier, variableEntry));
    }

    @Override
    public Result<VariableEntry> updateVariable(String identifier, Object value) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Can't modify an uninitialized variable.");
        }
        VariableEntry oldVariableEntry = variables.get(identifier);
        if (!oldVariableEntry.isMutable()) {
            return new IncorrectResult<>("Cannot modify an inmutable variable.");
        }
        Types type = oldVariableEntry.type();
        VariableEntry newVariableEntry =
                entryFactory.createVariableEntry(type, value, oldVariableEntry.isMutable());
        return new CorrectResult<>(variables.put(identifier, newVariableEntry));
    }

    @Override
    public Result<VariableEntry> readVariable(String identifier) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>(
                    "There is no variable declared with identifier: " + identifier);
        }
        return new CorrectResult<>(variables.get(identifier));
    }

    @Override
    public Boolean isVariableDeclared(String identifier) {
        return variables.containsKey(identifier);
    }

    @Override
    public Boolean isVariableInitialized(String identifier) {
        Result<VariableEntry> getVariableResult = readVariable(identifier);
        if (!getVariableResult.isCorrect()) {
            return false;
        }
        Object value = getVariableResult.result().value();
        return value != null;
    }

    @Override
    public Result<FunctionEntry> createFunction(
            String identifier, Map<String, Types> arguments, Types returnType) {
        if (isFunctionDeclared(identifier)) {
            return new IncorrectResult<>("Function is already declared.");
        }
        FunctionEntry functionEntry =
                entryFactory.createFunctionEntry(returnType, arguments, null, this);
        functions.put(identifier, functionEntry);
        return new CorrectResult<>(functionEntry);
    }

    @Override
    public Result<FunctionEntry> readFunction(String identifier) {
        if (!isFunctionDeclared(identifier)) {
            return new IncorrectResult<>("Function is not defined.");
        }
        return new CorrectResult<>(functions.get(identifier));
    }

    @Override
    public Result<FunctionEntry> updateFunction(String identifier, List<ExpressionNode> body) {
        if (!isFunctionDeclared(identifier)) {
            return new IncorrectResult<>("Function is not declared.");
        }
        FunctionEntry oldFunctionEntry = functions.get(identifier);
        FunctionEntry newFunctionEntry =
                entryFactory.createFunctionEntry(
                        oldFunctionEntry.returnType(), oldFunctionEntry.arguments(), body, this);
        functions.put(identifier, newFunctionEntry);
        return new CorrectResult<>(newFunctionEntry);
    }

    @Override
    public Boolean isFunctionDeclared(String identifier) {
        return functions.containsKey(identifier);
    }

    @Override
    public Boolean isFunctionInitialized(String identifier) {
        if (!isFunctionDeclared(identifier)) {
            return false;
        }
        return functions.get(identifier).body() != null;
    }

    @Override
    public Boolean isIdentifierDeclared(String identifier) {
        return isVariableDeclared(identifier) || isFunctionDeclared(identifier);
    }

    @Override
    public Boolean isIdentifierInitialized(String identifier) {
        return isVariableInitialized(identifier) || isFunctionDeclared(identifier);
    }

    @Override
    public Map<String, VariableEntry> readAll() {
        return Map.copyOf(variables);
    }

    @Override
    public Result<VariableEntry> createVariable(String identifier, Types type, Object value) {
        if (isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Cannot declare already created variable: " + identifier);
        }
        VariableEntry variableEntry = entryFactory.createVariableEntry(type, value, false);
        this.variables.put(identifier, variableEntry);
        return new CorrectResult<>(variableEntry);
    }

    @Override
    public Result<VariableEntry> deleteVariable(String identifier) {
        if (!isVariableDeclared(identifier)) {
            return new IncorrectResult<>("Tryied deleting a non-existing variable.");
        }
        return new CorrectResult<>(variables.remove(identifier));
    }
}
