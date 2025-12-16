/*
 * My Project
 */

package com.ingsis.utils.evalstate.env.factories;

import com.ingsis.utils.evalstate.env.Environment;
import com.ingsis.utils.evalstate.env.ScopedEnviroment;
import com.ingsis.utils.evalstate.env.bindings.Binding.FunctionBinding;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;
import com.ingsis.utils.type.types.Types;
import com.ingsis.utils.value.Value;
import com.ingsis.utils.value.Value.BooleanValue;
import com.ingsis.utils.value.Value.GlobalFunctionValue;
import com.ingsis.utils.value.Value.IntValue;
import com.ingsis.utils.value.Value.StringValue;
import java.util.List;
import java.util.Map;

public record DefaultEnviromentFactory() implements EnvironmentFactory {

    @Override
    public Environment createRoot(OutputEmitter emitter, InputSupplier supplier) {
        Environment env = new ScopedEnviroment(null, Map.of());
        env =
                env.define(
                        "println",
                        new FunctionBinding(
                                new GlobalFunctionValue(
                                        Map.of("string", Types.STRING),
                                        Types.NIL,
                                        (List<Value> args) -> {
                                            emitter.emit(getPrintableValue(args.get(0)));
                                            return Value.UnitValue.INSTANCE;
                                        })));
        env =
                env.define(
                        "readEnv",
                        new FunctionBinding(
                                new GlobalFunctionValue(
                                        Map.of("name", Types.STRING),
                                        Types.UNDEFINED,
                                        (List<Value> args) -> {
                                            String varName = ((StringValue) args.get(0)).v();
                                            String varValue = System.getenv(varName);
                                            return new StringValue(varValue);
                                        })));
        env =
                env.define(
                        "readInput",
                        new FunctionBinding(
                                new GlobalFunctionValue(
                                        Map.of("prompt", Types.STRING),
                                        Types.STRING,
                                        (List<Value> args) -> {
                                            emitter.emit(getPrintableValue(args.get(0)));
                                            return supplier.supply();
                                        })));
        return env;
    }

    private String getPrintableValue(Value value) {
        return switch (value) {
            case IntValue I -> I.v().stripTrailingZeros().toPlainString();
            case BooleanValue B -> Boolean.toString(B.v());
            case StringValue S -> S.v();
            default -> "";
        };
    }
}
