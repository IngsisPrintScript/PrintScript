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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
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
                                    Scanner scanner =
                                            new Scanner(System.in, StandardCharsets.UTF_8);
                                    Object input = scanner.nextLine();
                                    return input;
                                })));
        global.createFunction("readEnv", Map.of("string", Types.STRING), Types.UNDEFINED);
        global.updateFunction(
                "readEnv",
                List.of(
                        new GlobalFunctionBody(
                                List.of("string"),
                                args -> {
                                    if (args.length == 0 || args[0] == null) return null;
                                    String varName = args[0].toString();
                                    String env = System.getenv(varName);
                                    if (env == null) return "";
                                    return env;
                                })));
        return;
    }
}
