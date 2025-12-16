/*
 * My Project
 */

package com.ingsis.utils.evalstate.env.semantic;

import com.ingsis.utils.evalstate.env.semantic.bindings.SemanticBinding;
import java.util.Optional;

public sealed interface SemanticEnvironment permits ScopedSemanticEnvironment {

    SemanticEnvironment define(String identifier, SemanticBinding binding);

    Optional<SemanticBinding> lookup(String identifier);

    SemanticEnvironment enterScope();
}
