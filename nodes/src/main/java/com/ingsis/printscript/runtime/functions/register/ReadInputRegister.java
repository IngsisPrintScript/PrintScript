package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.runtime.functions.BuiltInFunction;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public final class ReadInputRegister extends BuiltInFunctionsRegister {
    private static final Scanner SCANNER = new Scanner(System.in);

    public ReadInputRegister() {}
    @Override
    public Map<String, BuiltInFunction> getBuiltInFunctions() {
        Map<String, BuiltInFunction> functionMap = new ConcurrentHashMap<>();
        functionMap.put(
                "readInput",
                new BuiltInFunction(
                        List.of(String.class),
                        Object.class,
                        args -> {
                            System.out.println(args.get(0));
                            return SCANNER.nextLine();
                        }
                )
        );
        return functionMap;
    }
}
