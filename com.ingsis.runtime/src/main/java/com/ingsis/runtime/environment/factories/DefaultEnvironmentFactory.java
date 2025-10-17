/*
 * My Project
 */

package com.ingsis.runtime.environment.factories;

import java.util.HashMap;
import java.util.Map;

import com.ingsis.runtime.environment.DefaultEnvironment;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.GlobalEnvironment;
import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.runtime.environment.entries.factories.EntryFactory;
import com.ingsis.types.Types;

public final class DefaultEnvironmentFactory implements EnvironmentFactory {
  private final EntryFactory entryFactory;

  public DefaultEnvironmentFactory(EntryFactory entryFactory) {
    this.entryFactory = entryFactory;
  }

  @Override
  public Environment createGlobalEnvironment() {
    return new GlobalEnvironment(entryFactory, createGlobalVariables(), createGlobalFunctions());
  }

  @Override
  public Environment createLocalEnvironment(Environment father) {
    return new DefaultEnvironment(entryFactory, father);
  }

  private Map<String, VariableEntry> createGlobalVariables() {
    return Map.of();
  }

  private Map<String, FunctionEntry> createGlobalFunctions() {
    Map<String, FunctionEntry> functions = new HashMap<>();
    functions.put("println", entryFactory.createFunctionEntry(Types.NIL, Map.of("string", Types.STRING), null));
    return functions;
  }
}
