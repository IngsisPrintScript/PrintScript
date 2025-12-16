/*
 * My Project
 */

package com.ingsis.utils.evalstate.env.bindings;

import com.ingsis.utils.type.types.Types;
import com.ingsis.utils.value.Value;
import com.ingsis.utils.value.Value.GlobalFunctionValue;
import java.util.Optional;

public sealed interface Binding {
    Types type();

    boolean isInitialized();

    public record VariableBinding(Types type, boolean isMutable, Optional<Value> value)
            implements Binding {

        @Override
        public boolean isInitialized() {
            return value.isPresent();
        }
    }

    public record FunctionBinding(GlobalFunctionValue value) implements Binding {

        @Override
        public boolean isInitialized() {
            return true;
        }

        @Override
        public Types type() {
            return value.returnType();
        }
    }
}
