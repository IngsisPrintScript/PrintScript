package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.runtime.functions.BuiltInFunction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PrintRegister extends  BuiltInFunctionsRegister {
    public PrintRegister() {

    }
    @Override
    public Map<String, BuiltInFunction> getBuiltInFunctions() {
        Map<String, BuiltInFunction> functionMap = new ConcurrentHashMap<>();
        functionMap.put(
                "println",
                new BuiltInFunction(
                        List.of(Object.class),
                        Void.class,
                        args -> {System.out.println(args.get(0)); return null;}
                )
        );
        return functionMap;
    }
}
