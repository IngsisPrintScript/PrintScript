
package com.ingsis.utils.runtime.environment.factories;

import com.ingsis.utils.nodes.expressions.function.GlobalFunctionBody;
import com.ingsis.utils.runtime.DefaultRuntime;
import com.ingsis.utils.runtime.environment.DefaultEnvironment;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.runtime.environment.GlobalEnvironment;
import com.ingsis.utils.runtime.environment.entries.factories.EntryFactory;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
public final class DefaultEnvironmentFactory implements EnvironmentFactory {
    private final EntryFactory entryFactory;
    private static final String PARAM_STRING = "string";

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
        global.createFunction(
                "println", new LinkedHashMap<>(Map.of(PARAM_STRING, Types.STRING)), Types.NIL);
        global.updateFunction(
                "println",
                List.of(
                        new GlobalFunctionBody(
                                List.of(PARAM_STRING),
                                args -> {
                                    DefaultRuntime.getInstance()
                                            .getEmitter()
                                            .print(args[0] == null ? "null" : args[0].toString());
                                    return null;
                                },
                                null,
                                null)));
        global.createFunction("readInput", new LinkedHashMap<>(), Types.UNDEFINED);

        global.updateFunction(
                "readInput",
                List.of(
                        new GlobalFunctionBody(
                                List.of(),
                                args -> {
                                    Scanner scanner =
                                            new Scanner(System.in, StandardCharsets.UTF_8);
                                    return scanner.nextLine();
                                },
                                null,
                                null)));
        global.createFunction("readNumber", new LinkedHashMap<>(), Types.NUMBER);
        global.updateFunction(
                "readNumber",
                List.of(
                        new GlobalFunctionBody(
                                List.of(),
                                args -> {
                                    Scanner scanner =
                                            new Scanner(System.in, StandardCharsets.UTF_8);
                                    String value = scanner.nextLine();
                                    try {
                                        return Double.parseDouble(value);
                                    } catch (NumberFormatException e) {
                                        return 0.0;
                                    }
                                },
                                null,
                                null)));
        global.createFunction(
                "readEnv",
                new LinkedHashMap<>(Map.of(PARAM_STRING, Types.STRING)),
                Types.UNDEFINED);
        global.updateFunction(
                "readEnv",
                List.of(
                        new GlobalFunctionBody(
                                List.of(PARAM_STRING),
                                args -> {
                                    if (args.length == 0 || args[0] == null) return "";
                                    String varName = args[0].toString();
                                    String env = System.getenv(varName);
                                    return env == null ? "" : env;
                                },
                                null,
                                null)));
        return;
    }
}