package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.runtime.functions.BuiltInFunction;

import java.util.List;
import java.util.Map;

public final class PrintRegister extends  BuiltInFunctionsRegister {
    public PrintRegister() {

    }
    @Override
    public void registerBuiltInFunctions(Map<String, BuiltInFunction> functionMap) {
        functionMap.put(
                "println",
                new BuiltInFunction(
                        List.of(Object.class),
                        Void.class,
                        args -> {System.out.println(args.get(0)); return null;}
                )
        );
    }
}
