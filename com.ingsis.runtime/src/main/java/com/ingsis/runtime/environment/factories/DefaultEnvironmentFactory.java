/*
 * My Project
 */

package com.ingsis.runtime.environment.factories;

import com.ingsis.nodes.expression.function.GlobalFunctionBody;
import com.ingsis.runtime.environment.DefaultEnvironment;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.GlobalEnvironment;
import com.ingsis.runtime.environment.entries.factories.EntryFactory;
import com.ingsis.types.Types;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class DefaultEnvironmentFactory implements EnvironmentFactory {
  private final EntryFactory entryFactory;

  public DefaultEnvironmentFactory(EntryFactory entryFactory) {
    this.entryFactory = entryFactory;
  }

  @Override
  public Environment createGlobalEnvironment() {
    GlobalEnvironment global = new GlobalEnvironment(entryFactory);
    addGlobalFunctions(global);
    addGlobalVariables(global);
    return global;
  }

  @Override
  public Environment createLocalEnvironment(Environment father) {
    return new DefaultEnvironment(entryFactory, father);
  }

  private void addGlobalVariables(GlobalEnvironment global) {
    return;
  }

  private void addGlobalFunctions(GlobalEnvironment global) {
    global.createFunction("println", Map.of("string", Types.STRING), Types.NIL);
    global.updateFunction(
        "println",
        List.of(
            new GlobalFunctionBody(
                List.of("string"),
                args -> {
                  System.out.println(args[0]);
                  return null;
                })));
    global.createFunction("readInput", Map.of("string", Types.STRING), Types.UNDEFINED);
    global.updateFunction(
        "readInput",
        List.of(
            new GlobalFunctionBody(
                List.of("string"),
                args -> {
                  System.out.println(args[0]);
                  Scanner scanner = new Scanner(System.in);
                  Object input = scanner.nextLine();
                  return input;
                })));
    return;
  }
}
