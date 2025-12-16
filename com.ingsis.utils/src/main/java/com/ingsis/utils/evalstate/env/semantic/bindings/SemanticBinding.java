/*
 * My Project
 */

package com.ingsis.utils.evalstate.env.semantic.bindings;

import com.ingsis.utils.type.types.Types;

public sealed interface SemanticBinding {
    Types type();

    boolean isInitialized();

    public record VariableBinding(Types type, boolean isMutable, boolean initialized)
            implements SemanticBinding {

        @Override
        public boolean isInitialized() {
            return initialized;
        }
    }

    public record FunctionBinding(Types type) implements SemanticBinding {
        @Override
        public boolean isInitialized() {
            return true;
        }
    }
}
