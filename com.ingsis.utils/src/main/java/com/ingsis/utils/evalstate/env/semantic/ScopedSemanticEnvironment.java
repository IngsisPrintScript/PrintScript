/*
 * My Project
 */

package com.ingsis.utils.evalstate.env.semantic;

import com.ingsis.utils.evalstate.env.semantic.bindings.SemanticBinding;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record ScopedSemanticEnvironment(
        SemanticEnvironment father, Map<String, SemanticBinding> bindings)
        implements SemanticEnvironment {

    public ScopedSemanticEnvironment {
        bindings = Map.copyOf(bindings);
    }

    @Override
    public SemanticEnvironment define(String identifier, SemanticBinding binding) {
        Map<String, SemanticBinding> newBindings = new HashMap<>(bindings);
        newBindings.put(identifier, binding);
        return new ScopedSemanticEnvironment(father, Map.copyOf(newBindings));
    }

    @Override
    public Optional<SemanticBinding> lookup(String identifier) {
        SemanticBinding local = bindings.get(identifier);
        if (local != null) {
            return Optional.of(local);
        }
        return father != null ? father.lookup(identifier) : Optional.empty();
    }

    @Override
    public SemanticEnvironment enterScope() {
        return new ScopedSemanticEnvironment(this, Map.of());
    }
}
