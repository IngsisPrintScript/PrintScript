package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.functions.BuiltInFunction;

import java.util.List;
import java.util.Map;

public class ReadEnvRegister extends BuiltInFunctionsRegister {
    @Override
    public void registerBuiltInFunctions(Map<String, BuiltInFunction> functionMap) {
        functionMap.put(
                "readEnv",
                new BuiltInFunction(
                        List.of(String.class),
                        Object.class,
                        args -> Runtime.getInstance().currentEnv().getVariableValue((String) args.get(0))
                )
        );
    }
}
