package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.runtime.functions.BuiltInFunction;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class ReadInputRegister extends BuiltInFunctionsRegister {
    private static final Scanner SCANNER = new Scanner(System.in);

    public ReadInputRegister() {}
    @Override
    public void registerBuiltInFunctions(Map<String, BuiltInFunction> functionMap) {
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
    }
}
