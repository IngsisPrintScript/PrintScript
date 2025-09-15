package com.ingsis.printscript.runtime.functions.register;

import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.runtime.functions.BuiltInFunction;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class BuiltInFunctionsRegister {
    private final Collection<Class<? extends BuiltInFunctionsRegister>> subRegisters;

    public BuiltInFunctionsRegister() {
        subRegisters = Collections.unmodifiableCollection(
                new ClassGraphReflectionsUtils().findSubclassesOf(BuiltInFunctionsRegister.class).find()
        );
    }

    public void registerBuiltInFunctions(Map<String, BuiltInFunction> functionMap) {
        for (Class<? extends BuiltInFunctionsRegister> subRegisterClass : subRegisters) {
            try {
                BuiltInFunctionsRegister subRegister = subRegisterClass.getDeclaredConstructor().newInstance();
                subRegister.registerBuiltInFunctions(functionMap);
            } catch (RuntimeException rte){
                throw rte;
            } catch (Exception ignored){}
        }
    }
}
