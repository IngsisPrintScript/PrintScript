/*
 * My Project
 */

package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.runtime.functions.BuiltInFunction;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BuiltInFunctionsRegister {
    private final Collection<Class<? extends BuiltInFunctionsRegister>> subRegisters;

    public BuiltInFunctionsRegister() {
        subRegisters = List.of(
                PrintRegister.class,
                ReadEnvRegister.class,
                ReadInputRegister.class
        );
    }

    public Map<String, BuiltInFunction> getBuiltInFunctions() {
        Map<String, BuiltInFunction> builtInFunctionMap = new ConcurrentHashMap<>();
        for (Class<? extends BuiltInFunctionsRegister> subRegisterClass : subRegisters) {
            try {
                BuiltInFunctionsRegister subRegister =
                        subRegisterClass.getDeclaredConstructor().newInstance();
                builtInFunctionMap.putAll(subRegister.getBuiltInFunctions());
            } catch (RuntimeException rte) {
                throw rte;
            } catch (Exception ignored) {
            }
        }
        return builtInFunctionMap;
    }
}
