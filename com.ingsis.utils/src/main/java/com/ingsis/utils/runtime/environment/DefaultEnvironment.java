package com.ingsis.utils.runtime.environment;

import com.ingsis.utils.nodes.expressions.ExpressionNode;

/*
 * My Project
 */

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.environment.entries.FunctionEntry;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.runtime.environment.entries.factories.EntryFactory;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings(value = "EI2", justification = "Environment is a shared, controlled mutable dependency.")
public final class DefaultEnvironment implements Environment {
  private final EntryFactory entryFactory;
  private final Map<String, FunctionEntry> functions;
  private final Map<String, VariableEntry> variables;

  private final Environment father;

  public DefaultEnvironment(
      EntryFactory entryFactory,
      Map<String, VariableEntry> variables,
      Map<String, VariableEntry> values,
      Map<String, FunctionEntry> functions,
      Environment father) {
    this.entryFactory = entryFactory;
    this.functions = functions;
    this.variables = new HashMap<>(variables);
    this.father = father;
  }

  public DefaultEnvironment(EntryFactory entryFactory, Environment father) {
    this(entryFactory, new HashMap<>(), new HashMap<>(), new HashMap<>(), father);
  }

  private Map<String, VariableEntry> variables() {
    return variables;
  }

  private Environment father() {
    return father;
  }

  @Override
  public Result<VariableEntry> createVariable(
      String identifier, Types type, Object value, Boolean isMutable) {
    if (isVariableDeclared(identifier)) {
      return new IncorrectResult<>("Cannot declare already created variable: " + identifier);
    }
    VariableEntry variableEntry = entryFactory.createVariableEntry(type, value, isMutable);
    this.variables.put(identifier, variableEntry);
    return new CorrectResult<>(variableEntry);
  }

  @Override
  public Result<VariableEntry> createVariable(String identifier, Types type) {
    if (isVariableDeclared(identifier)) {
      return new IncorrectResult<>("Can't create an already created variable.");
    }
    VariableEntry variableEntry = entryFactory.createVariableEntry(type, null, true);
    return new CorrectResult<>(variables().put(identifier, variableEntry));
  }

  @Override
  public Result<VariableEntry> updateVariable(String identifier, Object value) {
    if (!isVariableDeclared(identifier)) {
      return new IncorrectResult<>("Can't modify an uninitialized variable.");
    } else if (!isVariableDeclaredHere(identifier)) {
      return father.updateVariable(identifier, value);
    }
    VariableEntry oldVariableEntry = variables().get(identifier);
    if (!oldVariableEntry.isMutable()) {
      return new IncorrectResult<>("Tried modifying an inmutable variable.");
    }
    Types type = oldVariableEntry.type();
    VariableEntry newVariableEntry = entryFactory.createVariableEntry(type, value, oldVariableEntry.isMutable());
    variables().put(identifier, newVariableEntry);
    return new CorrectResult<>(newVariableEntry);
  }

  @Override
  public Result<VariableEntry> readVariable(String identifier) {
    if (!isVariableDeclared(identifier)) {
      return new IncorrectResult<>(
          "There is no variable declared with identifier: " + identifier);
    } else if (!isVariableDeclaredHere(identifier)) {
      return father().readVariable(identifier);
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
    FunctionEntry functionEntry = entryFactory.createFunctionEntry(returnType, arguments, null, this);
    functions.put(identifier, functionEntry);
    return new CorrectResult<>(functionEntry);
  }

  @Override
  public Result<FunctionEntry> readFunction(String identifier) {
    if (!isFunctionDeclared(identifier)) {
      return new IncorrectResult<>("Function is not declared.");
    } else if (!isFunctionDeclaredHere(identifier)) {
      return father().readFunction(identifier);
    }
    return new CorrectResult<>(functions.get(identifier));
  }

  @Override
  public Result<FunctionEntry> updateFunction(String identifier, List<ExpressionNode> body) {
    if (!isFunctionDeclared(identifier)) {
      return new IncorrectResult<>("Function is not declared.");
    } else if (!isFunctionDeclaredHere(identifier)) {
      return father().updateFunction(identifier, body);
    }
    FunctionEntry oldFunctionEntry = functions.get(identifier);
    FunctionEntry newFunctionEntry = entryFactory.createFunctionEntry(
        oldFunctionEntry.returnType(), oldFunctionEntry.arguments(), body, this);
    functions.put(identifier, newFunctionEntry);
    return new CorrectResult<>(newFunctionEntry);
  }

  @Override
  public Boolean isFunctionDeclared(String identifier) {
    return isFunctionDeclaredHere(identifier) || father.isFunctionDeclared(identifier);
  }

  @Override
  public Boolean isFunctionInitialized(String identifier) {
    return isFunctionInitializedHere(identifier) || father.isFunctionInitialized(identifier);
  }

  private Boolean isFunctionDeclaredHere(String identifier) {
    return functions.containsKey(identifier);
  }

  private Boolean isFunctionInitializedHere(String identifier) {
    return isFunctionDeclaredHere(identifier) && functions.get(identifier).body() != null;
  }

  @Override
  public Boolean isIdentifierDeclared(String identifier) {
    return isVariableDeclared(identifier) || isFunctionDeclared(identifier);
  }

  @Override
  public Boolean isIdentifierInitialized(String identifier) {
    return isVariableInitialized(identifier) || isFunctionInitialized(identifier);
  }

  @Override
  public Map<String, VariableEntry> readAll() {
    return Map.copyOf(variables);
  }

  @Override
  public Result<VariableEntry> deleteVariable(String identifier) {
    if (!isVariableDeclared(identifier)) {
      return new IncorrectResult<>("Tryied deleting a non-existing variable.");
    } else if (!isVariableDeclaredHere(identifier)) {
      return new CorrectResult<>(variables.remove(identifier));
    }
    return father().deleteVariable(identifier);
  }
}
