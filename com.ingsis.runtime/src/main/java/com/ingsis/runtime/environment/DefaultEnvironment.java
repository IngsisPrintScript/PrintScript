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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.Map;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultEnvironment implements Environment {
  private final EntryFactory entryFactory;
  private final Map<String, VariableEntry> variables;
  private final Environment father;

  public DefaultEnvironment(
      EntryFactory entryFactory, Map<String, VariableEntry> variables, Environment father) {
    this.entryFactory = entryFactory;
    this.variables = new HashMap<>(variables);
    this.father = father;
  }

  public DefaultEnvironment(EntryFactory entryFactory, Environment father) {
    this(entryFactory, new HashMap<>(), father);
  }

  private Map<String, VariableEntry> variables() {
    return variables;
  }

  private Environment father() {
    return father;
  }

  @Override
  public Result<VariableEntry> putVariable(String identifier, Types type) {
    if (isVariableDeclared(identifier)) {
      return new IncorrectResult<>("Can't create an already created variable.");
    }
    VariableEntry variableEntry = entryFactory.createVariableEntry(type, null);
    return new CorrectResult<>(variables().put(identifier, variableEntry));
  }

  @Override
  public Result<VariableEntry> modifyVariable(String identifier, Object value) {
    if (!isVariableDeclared(identifier)) {
      return new IncorrectResult<>("Can't modify an uninitialized variable.");
    } else if (!isVariableDeclaredHere(identifier)) {
      return father.modifyVariable(identifier, value);
    }
    VariableEntry oldVariableEntry = variables().get(identifier);
    Types type = oldVariableEntry.type();
    VariableEntry newVariableEntry = entryFactory.createVariableEntry(type, value);
    return new CorrectResult<>(variables().put(identifier, newVariableEntry));
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
  public void setExecutionResult(Result<String> result) {
    father().setExecutionResult(result);
  }

  @Override
  public Result<String> getExecutionResult() {
    return father().getExecutionResult();
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
