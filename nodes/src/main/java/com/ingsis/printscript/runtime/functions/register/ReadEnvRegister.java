/*
 * My Project
 */

package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.runtime.functions.BuiltInFunction;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ReadEnvRegister extends BuiltInFunctionsRegister {
    @Override
    public Map<String, BuiltInFunction> getBuiltInFunctions() {
        Map<String, BuiltInFunction> functionMap = new ConcurrentHashMap<>();
        functionMap.put(
                "readEnv",
                new BuiltInFunction(
                        List.of(String.class),
                        Object.class,
                        args -> {
                            String path = args.get(0).toString();
                            return System.getenv(path);
                        }));
        return functionMap;
    }
}
